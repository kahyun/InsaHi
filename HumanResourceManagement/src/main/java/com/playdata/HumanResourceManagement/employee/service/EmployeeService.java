package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.AdminRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.Authentication;

public interface EmployeeService {

    Employee adminInsert(AdminRequestDTO adminRequestDTO);

    void addAdminAndUserRoles(Employee employee);

    Authentication login(LoginDTO employee);


    // Employee getUser(EmployeeRequestDTO employeeRequestDTO);
}
