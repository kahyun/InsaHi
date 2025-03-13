package com.playdata.attendanceSalary.atdSalDto.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.AllowanceEntity;
import com.playdata.attendanceSalary.atdSalEntity.sal.PayStubEntity;
import com.playdata.attendanceSalary.atdSalEntity.sal.SalaryEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllowanceResponseDTO {
    private Long allowanceId;
    private String companyCode;
    private boolean taxExemption;
    private BigDecimal salary;
    private Long salaryId;
    private Long payStubId;


    public AllowanceEntity toEntity(){
        AllowanceEntity allowanceEntity = new AllowanceEntity();
        allowanceEntity.setAllowanceId(this.allowanceId);
        allowanceEntity.setCompanyCode(this.companyCode);
        allowanceEntity.setTaxExemption(this.taxExemption);
        allowanceEntity.setSalary(this.salary);
        allowanceEntity.setSalaryId(this.salaryId);
        allowanceEntity.setPayStubId(this.payStubId);
        return allowanceEntity;
    }

}
