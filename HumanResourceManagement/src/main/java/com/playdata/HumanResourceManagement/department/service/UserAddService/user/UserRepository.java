package com.playdata.HumanResourceManagement.department.service.UserAddService.user;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Employee, Long> {

    // 직원 ID로 직원 조회
    Employee findByEmployeeId(String employeeId);

    // 직원 삭제
    void deleteByEmployeeId(String employeeId);

    boolean existsByEmployeeId(String employeeId);
}
