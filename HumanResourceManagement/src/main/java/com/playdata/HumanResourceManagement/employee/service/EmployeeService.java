package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import java.util.List;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.time.LocalTime;
//import org.springframework.security.core.Authentication;

public interface EmployeeService {

  Employee insertEmployee(EmployeeRequestDTO employeeRequestDTO);

  void addAdminAndUserRoles(Employee employee);

  Authentication signin(LoginDTO employee);

  EmployeeResponseDTO findEmployeeById(String employeeId);

  /// 김다울 추가
  LocalTime findCompanyStartTimeByEmployeeId(String employeeId);

  List<String> getAllEmployeeIds();

  // Employee getUser(EmployeeRequestDTO employeeRequestDTO);
}
