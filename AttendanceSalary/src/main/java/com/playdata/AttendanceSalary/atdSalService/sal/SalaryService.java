package com.playdata.attendanceSalary.atdSalService.sal;

import com.playdata.attendanceSalary.atdSalDto.sal.*;

import java.util.List;


public interface SalaryService {
    /// 급여계산 로직
    public PayStubResponseDTO calculateAndSaveEmployeePayStub(String employeeId);


    /// Position  서비스
    // 회사별 직급 생성
    PositionResponseDTO insertPosition(PositionResponseDTO requestDTO, String CompanyCode);

    // 직급 수정
    void updatePosition(PositionResponseDTO requestDTO);

    // 직급 삭제
    void deletePosition(PositionResponseDTO requestDTO);

    PositionResponseDTO findPosition(Long positionId);


    /// PositionSalaryStep 서비스
    PositionSalaryStepResponseDTO insertPositionSalaryStep(PositionSalaryStepResponseDTO responseDTO);

    void updatePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO);

    void deletePositionSalaryStep(PositionSalaryStepResponseDTO responseDTO);

    PositionSalaryStepResponseDTO findPositionSalaryStep(Long positionId);

    /// Allowance 서비스
    AllowanceResponseDTO insertAllowance(AllowanceResponseDTO responseDTO);

    void updateAllowance(AllowanceResponseDTO responseDTO);

    void deleteAllowance(AllowanceResponseDTO responseDTO);

    AllowanceResponseDTO findAllowance(Long allowanceId);

    List<AllowanceResponseDTO> findAllowancesByPayStubId(Long payStubId);

    /// Deduction 서비스
    DeductionResponseDTO insertDeduction(DeductionResponseDTO responseDTO);

    void updateDeduction(DeductionResponseDTO responseDTO);

    void deleteDeduction(DeductionResponseDTO responseDTO);

    DeductionResponseDTO findDeduction(Long deductionId);

    List<DeductionResponseDTO> findDeductionsByPayStubId(Long payStubId);

    /// paystub 서비스
    PayStubResponseDTO insertPayStub(PayStubResponseDTO responseDTO);

    void updateDeduction(PayStubResponseDTO responseDTO);

    void deleteDeduction(PayStubResponseDTO responseDTO);


    PayStubResponseDTO findPayStubByEmployeeId(String employeeId);

    /// Salary 서비스
    SalaryResponseDTO insertSalary(SalaryResponseDTO responseDTO);

    void updateSalary(SalaryResponseDTO responseDTO);

    void deleteSalary(SalaryResponseDTO responseDTO);

    SalaryResponseDTO findSalary(Long salaryId);


}
