package com.playdata.attendanceSalary.atdSalDto.sal;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionSalaryStepEntity;
import java.math.BigDecimal;

public class PositionSalaryStepResponseDTO {
    private Long id;
    private Long positionId;
    private int salaryStepId;
    private BigDecimal baseSalary;
    private BigDecimal positionAllowance;
    private BigDecimal overtimeRate;
    private int baseAnnualLeave;
    private BigDecimal salaryIncreaseAllowance;
    private String companyCode;


}
