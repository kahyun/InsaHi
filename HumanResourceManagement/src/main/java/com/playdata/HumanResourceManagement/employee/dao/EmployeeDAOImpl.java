package com.playdata.HumanResourceManagement.employee.dao;


import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

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
}
