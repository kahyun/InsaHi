package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.Authentication;

public interface EmployeeService {

    Employee insertEmployee(EmployeeRequestDTO employeeRequestDTO);

    void addAdminAndUserRoles(Employee employee);

    Authentication signin(LoginDTO employee);

    Employee findEmployeeById(String employeeId);

    // Employee getUser(EmployeeRequestDTO employeeRequestDTO);
}
