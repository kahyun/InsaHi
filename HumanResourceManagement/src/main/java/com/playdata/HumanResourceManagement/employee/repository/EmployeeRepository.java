package com.playdata.HumanResourceManagement.employee.repository;

import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findByEmployeeId(String employeeId);

    Employee findByEmployeeIdAndPasswordAndCompany(String employeeId, String Password, Company company);

    /// 김다울 - 회사 시간 가져오기
    @Query("SELECT e.company.startTime FROM Employee e WHERE e.employeeId = :employeeId")
    LocalTime findCompanyStartTimeByEmployeeId(@Param("employeeId") String employeeId);
}