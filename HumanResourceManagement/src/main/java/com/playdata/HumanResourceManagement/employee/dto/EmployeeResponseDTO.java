package com.playdata.HumanResourceManagement.employee.dto;

import java.time.LocalDate;
import java.util.Date;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private String employeeId;
    private String password;
    private String name;
    private String role;
    private String companyCode;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String birthday;
    private String departmentId;
    private String state;
    private Long positionSalaryId;
    private Date hireDate;
    private LocalDate retireDate;
    private String profileImage;
}
