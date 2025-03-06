package com.playdata.HumanResourceManagement.department.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequest {

    private String companyCode;  // 회사 코드
    private String departmentName;  // 부서명
    private Integer parentDepartmentId;  // 상위 부서 ID
    private int departmentLevel;  // 부서 계층
    private Integer leftNode;  // 왼쪽
    private Integer rightNode;  // 오른쪽

    // 새로 생성 시
    public DepartmentRequest(
            String companyCode,
            int departmentLevel
            )
    {
        this.companyCode = companyCode;
        this.departmentLevel = departmentLevel;
    }


    // 생성된 조직도 일 경우
    public DepartmentRequest(
            String companyCode,
            String departmentName,
            Integer parentDepartmentId,
            Integer leftNode,
            Integer rightNode
    ) {
        this.companyCode = companyCode;
        this.departmentName = departmentName;
        this.parentDepartmentId = parentDepartmentId;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }


}
