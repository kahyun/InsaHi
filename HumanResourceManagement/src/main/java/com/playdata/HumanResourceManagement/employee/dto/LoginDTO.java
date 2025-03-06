package com.playdata.HumanResourceManagement.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String employeeId;
    private String password;
    private String companyCode;
}
