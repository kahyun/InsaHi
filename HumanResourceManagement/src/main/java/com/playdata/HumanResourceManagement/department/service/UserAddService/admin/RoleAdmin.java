package com.playdata.HumanResourceManagement.department.service.UserAddService.admin;

import com.playdata.HumanResourceManagement.department.service.UserAddService.UserAuthority;
import org.springframework.stereotype.Component;

@Component
public class RoleAdmin implements UserAuthority {
    
    // 사용자 회원가입
    // 
    
    
    
    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
}
