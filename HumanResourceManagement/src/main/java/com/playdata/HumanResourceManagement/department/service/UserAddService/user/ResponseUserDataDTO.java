package com.playdata.HumanResourceManagement.department.service.UserAddService.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseUserDataDTO {
    private String employeeId;      // 직원 ID
    private String name;            // 이름
    private String role;            // 역할
    private String companyCode;     // 회사 코드
    private String email;           // 이메일
    private String phoneNumber;     // 전화번호
    private String address;         // 주소
    private String gender;          // 성별
    private LocalDate birthday;     // 생일
    private String departmentId;    // 부서 ID
    private String state;           // 상태 (재직, 퇴사 등)
    private LocalDate hireDate;     // 입사일
    private LocalDate retireDate;   // 퇴사일
    private String positionName;    // 직급명
    private Long positionSalaryId;  // 직급 호봉 아이디
    private int salaryStepId;       // 직급 호봉 단계 ID
}
