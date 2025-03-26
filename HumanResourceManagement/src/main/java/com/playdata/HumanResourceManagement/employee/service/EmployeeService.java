package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.AdminRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeUpdateDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.dto.ProfileCardDTO;
import com.playdata.HumanResourceManagement.employee.dto.UpdatePasswordDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;

public interface EmployeeService {

    Employee adminInsert(AdminRequestDTO adminRequestDTO);

    void addAdminAndUserRoles(Employee employee);

    Authentication login(LoginDTO employee);

    // Employee getUser(EmployeeRequestDTO employeeRequestDTO);


    EmployeeResponseDTO findEmployeeById(String employeeId);

    /// 김다울 추가
    Optional<LocalTime> findCompanyStartTimeByEmployeeId(String employeeId);

    List<String> getAllEmployeeIds();

    // Employee getUser(EmployeeRequestDTO employeeRequestDTO);

    //mypage 왼쪽 작은 프로필
    ProfileCardDTO getProfileCard(String employeeId);

    EmployeeResponseDTO getEmployeeInfo(String employeeId);

    EmployeeResponseDTO updateEmployeeInfo(String employeeId, EmployeeUpdateDTO employeeUpdateDTO);

    void addUserRoles(Employee employee);

    Employee employeeInsert(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO updatePassword(String employeeId, UpdatePasswordDTO updatePasswordDTO);

    List<Employee> getMemberList();
}