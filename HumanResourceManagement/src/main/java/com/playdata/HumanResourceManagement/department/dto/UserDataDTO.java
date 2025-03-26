package com.playdata.HumanResourceManagement.department.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 직원 정보 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 제외 (필요 시 NON_EMPTY로 변경 가능)
public class UserDataDTO {

    private String employeeId;          // 직원 ID
    private String password;            // 비밀번호
    private String name;                // 이름
    private String role;                // 역할
    private String companyCode;         // 회사 코드
    private String email;               // 이메일
    private String phoneNumber;         // 전화번호
    private String address;             // 주소
    private String gender;              // 성별
    private LocalDate birthday;         // 생일
    private String departmentId;        // 부서 ID
    private String state;               // 상태 (직원 상태: 재직, 퇴사 등)
    private LocalDate hireDate;         // 입사일
    private LocalDate retireDate;       // 퇴사일
    private String positionName;        // 직급명 (직급 정보 추가)

    /**
     * 모든 필드를 포함하는 생성자
     */
    public UserDataDTO(String employeeId, String name, String positionName, String email, String phoneNumber, String address, String gender, LocalDate birthday, String departmentId, String status, LocalDate hireDate, LocalDate retireDate) {
        this.employeeId = employeeId;
        this.name = name;
        this.positionName = positionName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.birthday = birthday;
        this.departmentId = departmentId;
        this.state = status;
        this.hireDate = hireDate;
        this.retireDate = retireDate;
    }
}
