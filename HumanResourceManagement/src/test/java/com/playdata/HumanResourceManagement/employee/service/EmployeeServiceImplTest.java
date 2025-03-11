package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class EmployeeServiceImplTest {
    @Autowired
    private EmployeeServiceImpl employeeService;
    @Test
    @Transactional
    public void test() {
        String employeeId = "E001";
        employeeService.findEmployeeById(employeeId);

    }

}