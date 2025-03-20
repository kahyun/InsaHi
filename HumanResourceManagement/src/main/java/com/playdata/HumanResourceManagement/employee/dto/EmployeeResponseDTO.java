package com.playdata.HumanResourceManagement.employee.dto;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private String employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private String departmentId;
    private String teamId;

}
