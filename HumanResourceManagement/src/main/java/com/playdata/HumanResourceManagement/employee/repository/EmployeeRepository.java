package com.playdata.HumanResourceManagement.employee.repository;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    // 직원 ID로 직원 조회
    Optional<Employee> findByEmployeeId(String employeeId);

    // 회사 코드로 직원 조회
    List<Employee> findByCompany_CompanyCode(String companyCode);

    // 부서 정보로 직원 조회
    List<Employee> findByDepartment(DepartmentEntity department);

    // 회사 출근 시간 조회
    @Query("SELECT e.company.startTime FROM Employee e WHERE e.employeeId = :employeeId")
    Optional<LocalTime> findCompanyStartTimeByEmployeeId(@Param("employeeId") String employeeId);

    // 해당 부서에 직원이 있는지 체크하는 쿼리
    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.department = :departmentEntity")
    boolean existsByDepartment(@Param("departmentEntity") DepartmentEntity departmentEntity);

    // 부서 ID로 직원 목록 조회
    @Query("SELECT e FROM Employee e WHERE e.department.departmentId = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") String departmentId);
}
