package com.playdata.attendanceSalary.atdSalDto.sal;


import com.playdata.attendanceSalary.atdSalEntity.sal.DeductionEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeductionResponseDTO {
    private Long deductionId;
    private Long payrollDetailsId;
    private Long payrollItemId;
    private BigDecimal amount;
    private Long salaryId;
    private Long payStubId;

    public DeductionEntity toEntity(){
        DeductionEntity deductionEntity = new DeductionEntity();
        deductionEntity.setDeductionId(this.deductionId);
        deductionEntity.setPayrollDetailsId(this.payrollDetailsId);
        deductionEntity.setPayrollItemId(this.payrollItemId);
        deductionEntity.setAmount(this.amount);
        deductionEntity.setSalaryId(this.salaryId);
        deductionEntity.setPayStubId(this.payStubId);
        return deductionEntity;
    }
}
