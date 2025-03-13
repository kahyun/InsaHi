package com.playdata.HumanResourceManagement.employee.dao;

import com.playdata.HumanResourceManagement.employee.entity.Employee;

import java.time.LocalTime;

public interface EmployeeDAO {
    void insert(Employee employee);
    Employee findById(String employeeId);
    ///  김다울 추가
    LocalTime findCompanyStartTimeByEmployeeId(String employeeId);

}
