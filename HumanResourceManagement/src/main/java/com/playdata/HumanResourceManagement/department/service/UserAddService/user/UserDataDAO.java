package com.playdata.HumanResourceManagement.department.service.UserAddService.user;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDataDAO {

    private final EmployeeRepository userRepository;

    @Autowired
    public UserDataDAO(EmployeeRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Employee save(Employee employee) {
        return userRepository.save(employee);
    }

    public Optional<Employee> findByEmployeeId(String employeeId) {
        Optional<Employee> employee = userRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return employee;
    }

    public void deleteByEmployeeId(String employeeId) {
        Optional<Employee> employee = findByEmployeeId(employeeId);
        userRepository.deleteById(String.valueOf(employee));
    }

    public boolean existsByEmployeeId(String employeeId) {
        return userRepository.existsByEmployeeId(employeeId);
    }
}
