package com.playdata.attendanceSalary.atdSalService.sal;


import com.playdata.attendanceSalary.atdClient.HrmFeignClient;
import com.playdata.attendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import com.playdata.attendanceSalary.atdSalDao.sal.*;
import com.playdata.attendanceSalary.atdSalDto.sal.*;
import com.playdata.attendanceSalary.atdSalEntity.sal.*;
import com.playdata.attendanceSalary.atdSalService.atd.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceImpl implements SalaryService {

    private final PositionDao positionDao;
    private final PositionSalaryDao positionSalaryDao;
    private final ModelMapper modelMapper;
    private final AllowanceDao allowanceDao;
    private final DeductionDao deductionDao;
    private final PayStubDao payStubDao;
    private final SalaryDao salaryDao;
    private final AttendanceService attendanceService;
    private final HrmFeignClient hrmFeignClient;

    /// 급여 계산 로직
    @Override
    public PayStubResponseDTO calculateAndSaveEmployeePayStub(String employeeId) {
        //  1. 직원 정보 조회 (HRM 시스템 또는 Employee 시스템에서 조회)
        EmployeeResponseDTO employee = hrmFeignClient.findEmployee(employeeId); // ✅ 수정: 인스턴스 호출로 변경
        if (employee == null) {
            throw new RuntimeException("직원 정보를 찾을 수 없습니다.");
        }

        //  2. 직급(Position) 정보 조회
        Long positionId = employee.getPositionId();
        if (positionId == null) {
            throw new RuntimeException("직원의 직급 정보를 찾을 수 없습니다.");
        }

        PositionResponseDTO position = findPosition(positionId);  //

        //  3. 포지션에 대한 급여스텝 조회
        PositionSalaryStepResponseDTO salaryStep = findPositionSalaryStep(positionId); //

        BigDecimal baseSalary = salaryStep.getBaseSalary();
        BigDecimal positionAllowance = salaryStep.getPositionAllowance();
        BigDecimal overtimeAllowance = salaryStep.getOvertimeAllowance();

        //  4. 연장근로시간 조회 및 수당 계산
        YearMonth currentMonth = YearMonth.now();
        BigDecimal monthlyOvertimeHours = attendanceService.calculateMonthlyOvertimeHours(employeeId, currentMonth);

        BigDecimal totalOvertimePay = overtimeAllowance.multiply(monthlyOvertimeHours);

        //  5. 기존 PayStub 조회 (직원 기준) -> 서비스 메서드 사용
        PayStubResponseDTO existingPayStubDTO = findPayStubByEmployeeId(employeeId);
        Long payStubId = (existingPayStubDTO != null) ? existingPayStubDTO.getPayStubId() : null;

        //  6. Allowance & Deduction 집계 (서비스 메서드 사용)
        BigDecimal totalAllowances = BigDecimal.ZERO;
        BigDecimal totalDeductions = BigDecimal.ZERO;

        if (payStubId != null) {
            List<AllowanceResponseDTO> allowances = findAllowancesByPayStubId(payStubId);  // ✅ 서비스 메서드 사용
            totalAllowances = allowances.stream()
                    .map(AllowanceResponseDTO::getSalary)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            List<DeductionResponseDTO> deductions = findDeductionsByPayStubId(payStubId);  // ✅ 서비스 메서드 사용
            totalDeductions = deductions.stream()
                    .map(DeductionResponseDTO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        //  7. 총 급여 계산
        BigDecimal totalBaseAndPositionSalary = baseSalary.add(positionAllowance);
        BigDecimal totalPayment = totalBaseAndPositionSalary.add(totalAllowances).add(totalOvertimePay);
        BigDecimal netPay = totalPayment.subtract(totalDeductions);

        //  8. PayStubEntity 생성 및 저장
        PayStubEntity payStubEntity = PayStubEntity.builder()
                .employeeId(employeeId)
                .baseSalary(totalBaseAndPositionSalary)
                .totalAllowances(totalAllowances)
                .overtimePay(totalOvertimePay)
                .totalPayment(totalPayment)
                .totalDeductions(totalDeductions)
                .netPay(netPay)
                .companyCode(employee.getCompanyCode())
                .paymentDate(LocalDateTime.now())
                .build();

        PayStubEntity savedPayStub = payStubDao.save(payStubEntity);

        // ✅ 9. DTO로 반환
        return modelMapper.map(savedPayStub, PayStubResponseDTO.class);
    }


    ///  Position 서비스
    // 회사에서 직급 추가
    @Override
    public PositionResponseDTO insertPosition(PositionResponseDTO requestDTO, String CompanyCode) {
        PositionEntity position = requestDTO.toEntity();
        PositionEntity pp = positionDao.savePosition(position);
        return modelMapper.map(pp, PositionResponseDTO.class);
    }

    @Override
    public void updatePosition(PositionResponseDTO requestDTO) {
        PositionEntity position = requestDTO.toEntity();
        positionDao.savePosition(position);
    }

    // 회사에서 직급 삭제
    @Override
    public void deletePosition(PositionResponseDTO requestDTO) {
        PositionEntity position = requestDTO.toEntity();
        positionDao.deletePosition(position);
    }

    @Override
    public PositionResponseDTO findPosition(Long positionId) {
        return modelMapper.map(positionDao.findById(positionId), PositionResponseDTO.class);
    }

    /// positionSalaryStep 서비스
    @Override
    public PositionSalaryStepResponseDTO insertPositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        PositionSalaryStepEntity ps = positionSalaryDao.savePositionSalaryStep(pss);
        PositionSalaryStepResponseDTO dto = modelMapper.map(ps, PositionSalaryStepResponseDTO.class);
        return dto;
    }

    @Override
    public void updatePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        positionSalaryDao.savePositionSalaryStep(pss);
    }

    @Override
    public void deletePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO) {
        PositionSalaryStepEntity pss = modelMapper.map(responseDTO, PositionSalaryStepEntity.class);
        positionSalaryDao.deletePosition(pss);
    }

    @Override
    public PositionSalaryStepResponseDTO findPositionSalaryStep(Long positionId) {
        return modelMapper.map(positionSalaryDao.findPositionSalaryStepById(positionId), PositionSalaryStepResponseDTO.class);
    }

    /// AllowanceResponse 서비스
    @Override
    public AllowanceResponseDTO insertAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = allowanceDao.saveAllowance(responseDTO.toEntity());
        return modelMapper.map(allowance, AllowanceResponseDTO.class);
    }

    @Override
    public void updateAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = modelMapper.map(responseDTO, AllowanceEntity.class);
        allowanceDao.saveAllowance(allowance);
    }

    @Override
    public void deleteAllowance(AllowanceResponseDTO responseDTO) {
        AllowanceEntity allowance = modelMapper.map(responseDTO, AllowanceEntity.class);
        allowanceDao.deleteAllowanceById(allowance.getAllowanceId());
    }

    @Override
    public AllowanceResponseDTO findAllowance(Long allowanceId) {
        return modelMapper.map(allowanceDao.findAllowanceById(allowanceId), AllowanceResponseDTO.class);
    }

    /// Deduction 서비스
    @Override
    public DeductionResponseDTO insertDeduction(DeductionResponseDTO responseDTO) {
        DeductionEntity dd = deductionDao.save(responseDTO.toEntity());
        return modelMapper.map(dd, DeductionResponseDTO.class);
    }

    @Override
    public void updateDeduction(DeductionResponseDTO responseDTO) {
        DeductionEntity deduction = modelMapper.map(responseDTO, DeductionEntity.class);
        deductionDao.save(deduction);
    }

    @Override
    public void deleteDeduction(DeductionResponseDTO responseDTO) {
        deductionDao.deleteById(responseDTO.getDeductionId());
    }

    @Override
    public DeductionResponseDTO findDeduction(Long deductionId) {
        return modelMapper.map(deductionDao.fetchById(deductionId), DeductionResponseDTO.class);
    }

    /// PayStub 서비스
    @Override
    public PayStubResponseDTO insertPayStub(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        PayStubEntity ps = payStubDao.save(pp);
        return modelMapper.map(ps, PayStubResponseDTO.class);
    }

    @Override
    public void updateDeduction(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        payStubDao.save(pp);
    }

    @Override
    public void deleteDeduction(PayStubResponseDTO responseDTO) {
        PayStubEntity pp = modelMapper.map(responseDTO, PayStubEntity.class);
        payStubDao.delete(responseDTO.toEntity());
    }

    @Override
    public PayStubResponseDTO findPayStubByEmployeeId(String employeeId) {
        PayStubEntity payStub = payStubDao.findPayStubByeEmployeeId(employeeId);
        return modelMapper.map(payStub, PayStubResponseDTO.class);
    }


    /// Salary 서비스
    @Override
    public SalaryResponseDTO insertSalary(SalaryResponseDTO responseDTO) {
        SalaryEntity salary = salaryDao.saveSalary(responseDTO.toEntity());
        return modelMapper.map(salary, SalaryResponseDTO.class);
    }

    @Override
    public void updateSalary(SalaryResponseDTO responseDTO) {
        SalaryEntity salary = salaryDao.saveSalary(responseDTO.toEntity());
        salaryDao.saveSalary(salary);
    }

    @Override
    public void deleteSalary(SalaryResponseDTO responseDTO) {
        salaryDao.deleteSalary(responseDTO.toEntity());
    }

    @Override
    public SalaryResponseDTO findSalary(Long salaryId) {
        return modelMapper.map(salaryDao.findSalaryById(salaryId), SalaryResponseDTO.class);
    }

    @Override
    public List<AllowanceResponseDTO> findAllowancesByPayStubId(Long payStubId) {
        List<AllowanceEntity> allowances = allowanceDao.findByPayStubId(payStubId);
        return allowances.stream()
                .map(entity -> modelMapper.map(entity, AllowanceResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeductionResponseDTO> findDeductionsByPayStubId(Long payStubId) {
        List<DeductionEntity> deductions = deductionDao.findByPayStubId(payStubId);
        return deductions.stream()
                .map(entity -> modelMapper.map(entity, DeductionResponseDTO.class))
                .collect(Collectors.toList());
    }
}
