package com.playdata.HumanResourceManagement.employee.dao;


import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EmployeeRepository employeeRepository;

    public void insert(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public Employee findById(String employeeId) {
        System.out.println("dao단 = " + employeeId);
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public LocalTime findCompanyStartTimeByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            log.warn("해당 ID의 직원이 없습니다: {}", employeeId);
            return null; // 혹은 예외처리 권장
        }
        if (employee.getCompany() == null) {
            log.warn("직원의 소속 회사 정보가 없습니다. 직원ID={}", employeeId);
            return null;
        }
        LocalTime companyStartTime = employee.getCompany().getStartTime();
        log.info("직원ID={}의 회사 출근 시간={}", employeeId, companyStartTime);
        return companyStartTime;
    }
}
