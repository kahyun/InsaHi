package com.playdata.HumanResourceManagement.department.dto;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@Builder
public class OrganizationDTO {
    private String departmentId;  // 부서 ID
    private String departmentName;  // 부서명
    private List<DownloadPositionDTO.PositionWithEmployeesDTO> positions;  // 직급별 직원 리스트
    private String companyCode;  // 회사 코드
    private String parentDepartmentId;  // 부모 부서 ID (새로 추가된 필드)

    // DepartmentEntity를 OrganizationDTO로 변환하는 메서드
    public static OrganizationDTO fromEntity(DepartmentEntity department, Object o, String operationType, String companyCode) {
        return OrganizationDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .companyCode(companyCode)
                .parentDepartmentId(department.getParentDepartmentId() != null ? department.getParentDepartmentId().getDepartmentId() : null)  // 부모 부서 ID 처리
                .build();
    }

    // 부모 부서 ID를 반환하는 메서드
    public String getParentDepartmentId() {
        return this.parentDepartmentId;
    }
}
