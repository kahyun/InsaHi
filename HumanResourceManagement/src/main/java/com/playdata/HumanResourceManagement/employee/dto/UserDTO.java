package com.playdata.HumanResourceManagement.employee.dto;

import com.playdata.HumanResourceManagement.employee.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String employeeId;
    private String password;
    private String companyCode;
    private String name;
    private String email;            // 이메일 추가
    private String phoneNumber;      // 전화번호 추가
    private String departmentId;     // 부서 ID 추가
    private String state;            // 상태 추가
    private Set<Authority> authorityList; // 권한 리스트 추가

    // 빌더 패턴을 사용하여 UserDTO 객체를 생성하는 메서드
    public static UserDTO.UserDTOBuilder builder() {
        return new UserDTO.UserDTOBuilder();
    }

    // 빌더 패턴을 사용하여 UserDTO 객체를 설정하는 내부 클래스
    public static class UserDTOBuilder {
        private String employeeId;
        private String password;
        private String companyCode;
        private String name;
        private String email;
        private String phoneNumber;
        private String departmentId;
        private String state;
        private Set<Authority> authorityList;

        // 필드 설정 메서드
        public UserDTOBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public UserDTOBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserDTOBuilder companyCode(String companyCode) {
            this.companyCode = companyCode;
            return this;
        }

        public UserDTOBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDTOBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDTOBuilder departmentId(String departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public UserDTOBuilder state(String state) {
            this.state = state;
            return this;
        }

        public UserDTOBuilder authorityList(Set<Authority> authorityList) {
            this.authorityList = authorityList;
            return this;
        }

        // 최종 객체 반환
        public UserDTO build() {
            return new UserDTO(employeeId, password, companyCode, name, email, phoneNumber, departmentId, state, authorityList);
        }
    }
}
