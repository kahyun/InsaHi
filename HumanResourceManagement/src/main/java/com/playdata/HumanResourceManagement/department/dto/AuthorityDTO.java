package com.playdata.HumanResourceManagement.department.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorityDTO {
    private int authorityId;
    private String authorityName;
    private String role;
}
