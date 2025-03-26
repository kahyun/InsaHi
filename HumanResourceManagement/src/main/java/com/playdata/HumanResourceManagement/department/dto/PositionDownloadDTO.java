package com.playdata.HumanResourceManagement.department.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class PositionDownloadDTO {

        private Long positionId;    // 직급 ID
        private String positionName; // 직급 이름
        private String companyCode;  // 회사 코드
        private String employeeId;   // 직원 코드

        /**
         * 직급 정보를 반환하는 메서드
         *
         * @return 직급 정보 목록
         */
        public Collection<PositionDownloadDTO> getPositions() {
                return List.of(this);
        }
}
