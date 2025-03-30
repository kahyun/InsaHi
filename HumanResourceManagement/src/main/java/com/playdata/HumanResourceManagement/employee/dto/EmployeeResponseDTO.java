package com.playdata.HumanResourceManagement.employee.dto;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String gender;
    private String birthday;
    private String departmentId;
    private String state;
    private Long positionSalaryId;
    private LocalDate hireDate;
    private LocalDate retireDate;

    public EmployeeResponseDTO(Employee employee) {
        this.employeeId = employee.getEmployeeId();
        this.password = employee.getPassword();
        this.name = employee.getName();
        this.companyCode = employee.getCompanyCode();
        this.email = employee.getEmail();
        this.phoneNumber = employee.getPhoneNumber();
        this.gender = employee.getGender();
        this.birthday = employee.getBirthday() != null ? employee.getBirthday().toString() : null;
        this.departmentId = employee.getDepartmentId();  // 정상적으로 departmentId 반환
        this.state = employee.getState();  // getStatus() 대신 state로 변경
        this.positionSalaryId = Long.valueOf(employee.getPositionSalaryId());
        this.hireDate = employee.getHireDate();
        this.retireDate = employee.getRetireDate();
    }
}
