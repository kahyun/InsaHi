package com.playdata.HumanResourceManagement.employee.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MyUserDetail extends User {

    private final UserDTO userDto;

    // 생성자: UserDetails를 초기화하는 메서드
    public MyUserDetail(UserDTO userDto, Collection<? extends GrantedAuthority> authorities) {
        // 사용자 ID, 비밀번호, 권한 목록을 부모 클래스 User에 전달
        super(userDto.getEmployeeId(), userDto.getPassword(), authorities);
        this.userDto = userDto;
    }

    // UserDTO를 반환하는 메서드
    public UserDTO getUserDto() {
        return userDto;
    }

    // 사용자 회사 코드 반환하는 메서드
    public String getCompanyCode() {
        return userDto.getCompanyCode();
    }
}
