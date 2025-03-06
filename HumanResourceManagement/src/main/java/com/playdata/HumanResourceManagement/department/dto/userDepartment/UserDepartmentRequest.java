package com.playdata.HumanResourceManagement.department.dto.userDepartment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDepartmentRequest {

    // Employee Entity
    private String employeeId;
    private String password; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private String phoneNumber; // 핸드폰
    private String address; // 주소
    private String state; // 상태

    // Department Entity
    private String departmentId;  // 부서 ID
    private String companyCode;  // 회사 코드
    private String departmentName;  // 부서명
    private Integer parentDepartmentId;  // 상위 부서 ID
    private Integer departmentLevel;  // 부서 계층
    private Integer leftNode;  // 왼쪽
    private Integer rightNode;  // 오른쪽
}
