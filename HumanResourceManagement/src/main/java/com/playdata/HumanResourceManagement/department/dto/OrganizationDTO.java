package com.playdata.HumanResourceManagement.department.dto;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;@Data
@Builder
public class OrganizationDTO {
    private String departmentId;
    private String departmentName;
    private String companyCode;
    private String parentDepartmentId;
    private List<OrganizationDTO> subDepartments; // 하위 부서 리스트
    private List<UserDataDTO> employees; // 직원 리스트

    // DepartmentEntity에서 DTO로 변환
    public static OrganizationDTO fromEntity(DepartmentEntity department, String companyCode) {
        OrganizationDTO dto = OrganizationDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .companyCode(companyCode)
                .parentDepartmentId(department.getParentDepartmentId() != null ? department.getParentDepartmentId().getDepartmentId() : null)
                .subDepartments(department.getSubDepartments().stream()
                        .map(subDept -> fromEntity(subDept, companyCode))
                        .collect(Collectors.toList()))
                .employees(new ArrayList<>())
                .build();

        return dto;
    }


    public static OrganizationDTO fromEntityAndUserData(DepartmentEntity department, List<UserDataDTO> userDataList, List<PositionDownloadDTO> positions) {
        OrganizationDTO dto = fromEntity(department, department.getCompanyCode());

        // 직원 데이터와 포지션을 부서에 설정
        if (userDataList != null) {
            dto.setEmployees(userDataList);
        }

        return dto;
    }

    public void setEmployees(List<UserDataDTO> employees) {
        if (employees != null) {
            this.employees = new ArrayList<>(employees);
        }
    }
}
