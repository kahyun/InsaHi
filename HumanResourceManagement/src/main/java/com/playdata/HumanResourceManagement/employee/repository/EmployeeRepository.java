package com.playdata.HumanResourceManagement.employee.repository;

import com.playdata.HumanResourceManagement.department.business.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.employee.entity.Authority;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    /**
     * 직원 ID로 직원 정보를 조회 (권한 목록 포함)
     */
    @EntityGraph(attributePaths = "authorityList")
    Optional<Employee> findByEmployeeId(String employeeId);

    /**
     * 회사 코드로 직원 목록을 조회
     */
    List<Employee> findByCompany_CompanyCode(String companyCode);

    /**
     * 모든 직원 목록 조회
     */
    List<Employee> findAll();

  Optional<Employee> findById(String employeeId);
  // 부서 ID로 직원 조회
    /**
     * 특정 부서에 속한 직원 목록 조회
     */
    List<Employee> findByDepartment(DepartmentEntity department);

    /**
     * 특정 부서에 직원이 존재하는지 확인
     */
    @Query("SELECT COUNT(e) > 0 FROM Employee e WHERE e.department = :departmentEntity")
    boolean existsByDepartment(@Param("departmentEntity") DepartmentEntity departmentEntity);

    /**
     * 부서 ID로 직원 목록 조회
     */
    List<Employee> findByDepartment_DepartmentId(String departmentId);

    /**
     * 직원 ID로 회사 출근 시간 조회
     */
    @Query("SELECT e.company.startTime FROM Employee e WHERE e.employeeId = :employeeId")
    Optional<LocalTime> findCompanyStartTimeByEmployeeId(@Param("employeeId") String employeeId);

  List<Employee> findByAuthorityList_AuthorityName(String authorityName);

  // 회사의 권한 조회
  @Query("SELECT DISTINCT a FROM Employee e JOIN e.authorityList a WHERE e.company.companyCode = :companyCode")
  List<Authority> findAuthoritiesByCompanyCode(@Param("companyCode") String companyCode);

    /**
     * 부서 ID를 이용하여 직원 목록을 조회
     */
    @Query("SELECT e FROM Employee e WHERE e.department.departmentId = :departmentId")
    List<Employee> findByDepartmentId(@Param("departmentId") String departmentId);
}