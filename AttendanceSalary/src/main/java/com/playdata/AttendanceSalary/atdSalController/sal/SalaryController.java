package com.playdata.attendanceSalary.atdSalController.sal;

import com.playdata.attendanceSalary.atdSalDto.sal.*;
import com.playdata.attendanceSalary.atdSalEntity.sal.*;
import com.playdata.attendanceSalary.atdSalService.sal.SalaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/salary")
@RequiredArgsConstructor
public class SalaryController {
    private final SalaryService salaryService;

    /// 1. Position 서비스
    @PostMapping("/position-insert")
    public PositionResponseDTO positionInsert(@RequestBody PositionResponseDTO responseDTO,
                                              @RequestParam String companyCode) {
        return salaryService.insertPosition(responseDTO, companyCode);

    }

    @PutMapping("/positon-update")
    public void positionupdate(@RequestBody PositionResponseDTO responseDTO) {
        salaryService.updatePosition(responseDTO);
    }

    @DeleteMapping("/positon-delete")
    public void positionDelete(@RequestBody PositionResponseDTO responseDTO) {
        salaryService.deletePosition(responseDTO);

    }

    @GetMapping("/positon-find")
    public PositionResponseDTO positionFind(@RequestParam Long positionId) {
        return salaryService.findPosition(positionId);
    }

    /// 2. PositionSalaryStep 서비스
    @PostMapping("step-insert")
    public PositionSalaryStepResponseDTO positionInsert(@RequestBody PositionSalaryStepResponseDTO responseDTO) {
        return salaryService.insertPositionSalaryStep(responseDTO);
    }

    @PutMapping("step-update")
    public void positionupdate(@RequestBody PositionSalaryStepResponseDTO responseDTO) {
        salaryService.updatePositionSalaryStep(responseDTO);
    }

    @DeleteMapping("step-delete")
    public void positionDelete(@RequestBody PositionSalaryStepResponseDTO responseDTO) {
        salaryService.deletePositionSalaryStep(responseDTO);
    }

    @GetMapping("/step-find")
    public PositionSalaryStepResponseDTO positionSalaryStepFind(@RequestParam Long id) {
        return salaryService.findPositionSalaryStep(id);

    }

    /// AllowanceResponseDTO
    @PostMapping("/allowance-insert")
    public AllowanceResponseDTO insertAllowance(AllowanceResponseDTO responseDTO) {
        return salaryService.insertAllowance(responseDTO);
    }

    @PutMapping("/allowance-update")
    public void updateAllowance(AllowanceResponseDTO responseDTO) {
        salaryService.updateAllowance(responseDTO);
    }

    @DeleteMapping("/allowance-delete")
    public void deleteAllowance(AllowanceResponseDTO responseDTO) {
        salaryService.deleteAllowance(responseDTO);
    }

    @GetMapping("/allowance-find")
    public AllowanceResponseDTO findAllowance(Long allowanceId) {
        return salaryService.findAllowance(allowanceId);
    }


    /// Deduction 서비스
    @PostMapping("deduction-insert")
    public DeductionResponseDTO insertDeduction(DeductionResponseDTO responseDTO) {
        return salaryService.insertDeduction(responseDTO);
    }

    @PutMapping("deduction-update")
    public void updateDeduction(DeductionResponseDTO responseDTO) {
        salaryService.updateDeduction(responseDTO);
    }

    @DeleteMapping("deduction-delete")
    public void deleteDeduction(DeductionResponseDTO responseDTO) {
        salaryService.deleteDeduction(responseDTO);
    }

    @GetMapping("deduction-find")
    public DeductionResponseDTO findDeduction(Long deductionId) {
        return salaryService.findDeduction(deductionId);
    }


    /// PayStub 서비스
    @PostMapping("/paystub-insert")
    public PayStubResponseDTO insertPayStub(PayStubResponseDTO responseDTO) {
        return salaryService.insertPayStub(responseDTO);
    }

    @PutMapping("/paystub-update")
    public void updateDeduction(PayStubResponseDTO responseDTO) {
        salaryService.updateDeduction(responseDTO);
    }

    @DeleteMapping("/paystub-delete")
    public void deleteDeduction(PayStubResponseDTO responseDTO) {
        salaryService.deleteDeduction(responseDTO);

    }

//    @GetMapping("/paystub-find")
//    public PayStubResponseDTO findPayStub(Long payStubId) {
//        return salaryService.findPayStub(payStubId);
//    }

    /// Salary 서비스
    @PostMapping("insert")
    public SalaryResponseDTO insertSalary(SalaryResponseDTO responseDTO) {
        return salaryService.insertSalary(responseDTO);
    }

    @PutMapping("update")
    public void updateSalary(SalaryResponseDTO responseDTO) {
        salaryService.updateSalary(responseDTO);
    }

    @DeleteMapping("delete")
    public void deleteSalary(SalaryResponseDTO responseDTO) {
        salaryService.deleteSalary(responseDTO);
    }

    @GetMapping("find")
    public SalaryResponseDTO findSalary(Long salaryId) {
        return salaryService.findSalary(salaryId);
    }

}
