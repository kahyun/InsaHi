package com.playdata.HumanResourceManagement.employee.dao;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeDAO {

  void insert(Employee employee);

  Employee findById(String employeeId);

  Employee findByEmployeeId(String employeeId);

  List<Employee> findAll();

  ///  김다울 추가
  Optional<LocalTime> findCompanyStartTimeByEmployeeId(String employeeId);


  void update(Employee employee);
}