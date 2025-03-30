package com.playdata.HumanResourceManagement.employee.NewDepartment.dto;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrganizationStructureDTO {

    private String departmentId;                      // 부서 ID
    private String departmentName;                    // 부서명
    private String parentDepartmentId;                // 상위 부서 ID
    private List<OrganizationStructureDTO> subDepartments; // 하위 부서 목록

    public OrganizationStructureDTO(Object departmentId, String root, Object parentDepartmentId, int i, Object o) {
    }

    /**
     * Entity → DTO 변환 메서드
     */
    public static OrganizationStructureDTO fromEntity(DepartmentEntity department) {
        if (department == null) throw new IllegalArgumentException("부서 정보가 없습니다.");

        // 하위 부서가 있을 경우만 재귀적으로 처리
        List<OrganizationStructureDTO> subDeptList = department.getSubDepartments() != null
                ? department.getSubDepartments().stream()
                .map(OrganizationStructureDTO::fromEntity)
                .collect(Collectors.toUnmodifiableList())
                : Collections.emptyList();

        return OrganizationStructureDTO.builder()
                .departmentId(department.getDepartmentId())   // 부서 ID
                .departmentName(department.getDepartmentName()) // 부서명
                .parentDepartmentId(                          // 상위 부서 ID
                        department.getParentDepartmentId() != null
                                ? department.getParentDepartmentId().getDepartmentId()
                                : null
                )
                .subDepartments(subDeptList)                  // 하위 부서 목록
                .build();
    }
}
