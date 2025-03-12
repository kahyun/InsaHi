package com.playdata.attendanceSalary.atdSalDto.sal;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionSalaryStepEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PositionSalaryStepResponseDTO {
    private Long id;//직급 호봉 아이디
    private Long positionId; // 직급 아이디
    private int salaryStepId; // 호봉
    private BigDecimal baseSalary; //기본금
    private BigDecimal positionAllowance; //직급 수당
    private BigDecimal overtimeAllowance; // 연장수당
    private int baseAnnualLeave; //기본 연차
    private String companyCode;



}
