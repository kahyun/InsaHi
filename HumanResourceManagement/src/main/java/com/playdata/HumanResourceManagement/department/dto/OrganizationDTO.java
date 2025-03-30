package com.playdata.HumanResourceManagement.department.dto;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrganizationDTO {
    private String departmentId;
    private String departmentName;
    private String companyCode;
    private String parentDepartmentId;
    private List<OrganizationDTO> subDepartments = new ArrayList<>();  // 하위 부서 빈 리스트 초기화
    private List<UserDataDTO> employees = new ArrayList<>();  // 직원 리스트 빈 리스트 초기화
    private List<PositionDownloadDTO> positions = new ArrayList<>();  // 직급 리스트 빈 리스트 초기화

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
                .employees(new ArrayList<>())  // 기본 빈 리스트로 설정
                .positions(new ArrayList<>())  // 기본 빈 리스트로 설정
                .build();

        return dto;
    }

    // 직급 정보와 사용자 데이터를 추가하여 DTO 생성
    public static OrganizationDTO fromEntityAndUserData(DepartmentEntity department, List<UserDataDTO> userDataList, List<PositionDownloadDTO> positions) {
        OrganizationDTO dto = fromEntity(department, department.getCompanyCode());

        // 직원 데이터와 직급 정보를 부서에 설정
        if (userDataList != null) {
            dto.setEmployees(userDataList);  // 직원 데이터 추가
        }

        if (positions != null) {
            dto.setPositions(positions);  // 직급 정보 추가
        }

        return dto;
    }
}
