package com.playdata.HumanResourceManagement.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class EmployeeDTO {
    private String name;
    private String companyCode;
    private String employeeId;
}
