package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
//import org.springframework.security.core.Authentication;

public interface EmployeeService {

    void insertEmployee(EmployeeRequestDTO employeeRequestDTO);

//    Authentication signin(LoginDTO employee);
}
