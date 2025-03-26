package com.playdata.AttendanceSalary.atdSalService.atd;

import com.playdata.AttendanceSalary.atdClient.HrmFeignClient;
import com.playdata.AttendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import com.playdata.AttendanceSalary.atdSalDao.atd.AttendanceDAO;
import com.playdata.AttendanceSalary.atdSalDto.atd.AttendanceDTO;
import com.playdata.AttendanceSalary.atdSalEntity.atd.AttendanceEntity;
import com.playdata.AttendanceSalary.atdSalEntity.atd.AttendanceStauts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceDAO attendanceDAO;
    private final HrmFeignClient hrmFeignClient;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDTO findAttendanceByEmployeeId(String employeeId) {
        AttendanceEntity attendance = attendanceDAO.findAttendanceByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("서비스 널 오류"));
        return modelMapper.map(attendance, AttendanceDTO.class);
    }

    @Override
    public List<AttendanceDTO> getAttendanceByEmployeeId(String employeeId) {
        List<AttendanceEntity> entities = attendanceDAO.findByEmployeeId(employeeId);
        return entities.stream()
                .map(attendanceEntity -> modelMapper.map(attendanceEntity, AttendanceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculateMonthlyOvertimeHours(String employeeId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        BigDecimal totalOvertime = attendanceDAO.getTotalOvertimeHoursByEmployeeAndDateRange(employeeId, startDate, endDate);
        log.info("직원 ID: {}의 {}월 연장 근무 시간 합계: {}", employeeId, yearMonth.getMonthValue(), totalOvertime);
        return totalOvertime;
    }

    @Override
    public AttendanceDTO checkIn(String employeeId) throws IllegalAccessException {
        EmployeeResponseDTO emp = hrmFeignClient.findEmployee(employeeId);

        // 1) null 처리 후 진행
        if (!attendanceDAO.findCheckOutTimeByEmployeeId(employeeId).isEmpty()) {
            throw new RuntimeException("퇴근 처리가 되지 않았습니다. 퇴근을 먼저 해주세요.");
        }

        AttendanceEntity attendanceEntity = new AttendanceEntity();
        attendanceEntity.setEmployeeId(emp.getEmployeeId());
        attendanceEntity.setCompanyCode(emp.getCompanyCode());

        // 회사별 출근 시간 지정
        LocalTime companyHour = hrmFeignClient.getCompanyStartTime(employeeId).getBody();
        log.info("서비스단 companyHour = {}", companyHour);

        attendanceEntity.setWorkDate(LocalDate.now());
        LocalDateTime todayTime = LocalDateTime.of(LocalDate.now(), companyHour);
        boolean isWeekday = (todayTime.getDayOfWeek() != DayOfWeek.SATURDAY && todayTime.getDayOfWeek() != DayOfWeek.SUNDAY);

        // 출근 시간 설정 로직
        LocalDateTime checkInTime = LocalDateTime.now();
        if (LocalTime.now().isBefore(companyHour) && isWeekday) {
            attendanceEntity.setAttendanceStatus(AttendanceStauts.ATTENDANCE);
            attendanceEntity.setCheckInTime(LocalDateTime.of(LocalDate.now(), companyHour));
        } else if (LocalTime.now().isAfter(companyHour) && isWeekday) {
            attendanceEntity.setAttendanceStatus(AttendanceStauts.TARDINESS);
            attendanceEntity.setCheckInTime(checkInTime);
        } else if (!isWeekday) {
            attendanceEntity.setCheckInTime(checkInTime);
            attendanceEntity.setAttendanceStatus(AttendanceStauts.WEEKEND_WORK);
        }

        return modelMapper.map(attendanceDAO.save(attendanceEntity), AttendanceDTO.class);
    }

    @Override
    public void checkOut(Long id) {
        AttendanceEntity attendance = attendanceDAO.findById(id);
        LocalDateTime now = LocalDateTime.now();
        attendance.setCheckOutTime(now); // 퇴근 시간 기록

        // 근무 시간 계산
        Duration duration = Duration.between(attendance.getCheckInTime(), now);
        long workMinutes = Math.max(0, duration.toMinutesPart());
        long workHours = Math.max(0, duration.toHours());

        // 주말 확인
        DayOfWeek today = now.getDayOfWeek();
        boolean isWeekend = (today == DayOfWeek.SATURDAY || today == DayOfWeek.SUNDAY);

        // 연장 근무 시간 계산 (법정 근무 시간: 8시간)
        long overtimeMinutesTotal = 0;
        if (!isWeekend) {
            if (workHours > 8 || (workHours == 8 && workMinutes > 0)) {
                overtimeMinutesTotal = ((workHours - 8) * 60) + workMinutes;
            }
        } else {
            overtimeMinutesTotal = (workHours * 60) + workMinutes;
        }

        // 근무 시간 및 연장 근무 시간 저장
        BigDecimal todayWorkHours = BigDecimal.valueOf(workMinutes).add(BigDecimal.valueOf(workHours * 60));
        BigDecimal todayOvertimeWorkHours = BigDecimal.valueOf(overtimeMinutesTotal)
                .divide(BigDecimal.valueOf(60), 3, RoundingMode.HALF_UP); // 소수점 3자리 반올림

        attendance.setWorkHours(todayWorkHours); // 총 근무 시간 (분 단위)
        attendance.setOvertimeHours(todayOvertimeWorkHours);

        // 데이터 저장
        attendanceDAO.save(attendance);
    }
}
