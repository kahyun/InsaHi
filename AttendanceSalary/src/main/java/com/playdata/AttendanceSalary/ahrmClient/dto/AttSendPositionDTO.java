package com.playdata.AttendanceSalary.ahrmClient.dto;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 직급(Position) 및 관련 정보 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttSendPositionDTO {

    private Long positionId;
    private String positionName;
    private String companyCode;

    private List<SalaryStepDTO> salarySteps;

    public static AttSendPositionDTO fromEntity(PositionEntity entity) {
        return AttSendPositionDTO.builder()
                .positionId(entity.getPositionId())
                .positionName(entity.getPositionName())
                .companyCode(entity.getCompanyCode())
                .salarySteps(
                        entity.getSalarySteps().stream()
                                .map(SalaryStepDTO::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
