package com.playdata.attendanceSalary.atdSalRepository.sal;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.attendanceSalary.atdSalEntity.sal.SeverancePayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeverancePayRepository extends JpaRepository<SeverancePayEntity, Employee> {
}
