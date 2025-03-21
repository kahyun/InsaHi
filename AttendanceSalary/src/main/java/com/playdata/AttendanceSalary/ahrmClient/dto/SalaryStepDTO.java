package com.playdata.AttendanceSalary.ahrmClient.dto;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionSalaryStepEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryStepDTO {

    private Long positionSalaryId;
    private int salaryStepId;
    private BigDecimal baseSalary;
    private BigDecimal overtimeAllowance;
    private int baseAnnualLeave;

    public static SalaryStepDTO fromEntity(PositionSalaryStepEntity entity) {
        return SalaryStepDTO.builder()
                .positionSalaryId(entity.getPositionSalaryId())
                .salaryStepId(entity.getSalaryStepId())
                .baseSalary(entity.getBaseSalary())
                .overtimeAllowance(entity.getOvertimeAllowance())
                .baseAnnualLeave(entity.getBaseAnnualLeave())
                .build();
    }
}
