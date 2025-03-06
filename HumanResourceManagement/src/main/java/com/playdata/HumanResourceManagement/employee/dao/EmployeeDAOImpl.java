package com.playdata.HumanResourceManagement.employee.dao;


import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeDAOImpl implements EmployeeDAO {

    private final EmployeeRepository employeeRepository;

    public void insert(Employee employee) {
        employeeRepository.save(employee);
    }
}
