package com.playdata.AttendanceSalary.atdSalController.atd;

import com.playdata.AttendanceSalary.atdSalDto.atd.AttendanceDTO;
import com.playdata.AttendanceSalary.atdSalEntity.atd.AttendanceEntity;
import com.playdata.AttendanceSalary.atdSalService.atd.AttendanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내 출근시간이랑, 회사 출근시간 계산 다르개 출근  버튼 , 퇴근
 */
@Slf4j
@RestController
@RequestMapping("/atdsal")
@RequiredArgsConstructor
public class AttendanceController {

  private final AttendanceService attendanceService;

  @GetMapping("/checkin/{employeeId}")
  public List<AttendanceDTO> getAttendanceByEmployeeId(
      @PathVariable("employeeId") String employeeId) {
    List<AttendanceDTO> attendanceDTOList = attendanceService.getAttendanceByEmployeeId(employeeId);
    return attendanceDTOList;
  }


  @PostMapping("/checkin")
  public ResponseEntity<?> checkIn(@RequestParam("employeeId") String employeeId) throws Exception {
    AttendanceDTO attendanceDTO = attendanceService.checkIn(employeeId);
    log.info(attendanceDTO.toString());
    return ResponseEntity.ok(attendanceDTO);
  }

    //  ResponseEntity<?> 으로 교체예정
    @PutMapping("/check-out")
    public void checkOut(@RequestParam("employeeId") String employeeId) {
        // employId를 통해 attenanceId를 찾아서
       AttendanceDTO attendanceDTO = attendanceService.findAttendanceByEmployeeId(employeeId);
       log.info("Controller:" + attendanceDTO.toString());
        System.out.println("attendanceDTO = " + attendanceDTO.getAttendanceId());
       attendanceService.checkOut(attendanceDTO.getAttendanceId());
    }
}