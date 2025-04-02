package com.playdata.HumanResourceManagement.department.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PositionDownloadDTO {
        private Long positionSalaryId;
        private String positionName;
        private int salaryStepId;
        private String companyCode;
        private String employeeId;

        // 직급 목록을 포함하는 필드
        private List<PositionDownloadDTO> positions;

        // 직급 목록을 반환하는 메서드
        public List<PositionDownloadDTO> getPositions() {
                return positions;
        }
}
