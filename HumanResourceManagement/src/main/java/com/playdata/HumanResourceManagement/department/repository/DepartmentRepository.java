package com.playdata.HumanResourceManagement.department.repository;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, String> {

  // 1. 회사 코드로 모든 부서 조회
  List<DepartmentEntity> findAllByCompanyCode(String companyCode);

  // 2. 회사 코드와 부서 ID로 부서 삭제
  @Modifying
  @Transactional
  @Query("DELETE FROM DepartmentEntity d WHERE d.companyCode = :companyCode AND d.departmentId = :departmentId")
  void deleteByCompanyCodeAndDepartmentId(@Param("companyCode") String companyCode, @Param("departmentId") String departmentId);

  // 3. 부모 부서 ID로 하위 부서 조회
  List<DepartmentEntity> findByParentDepartmentId(DepartmentEntity parent);

  // 4. 회사 코드로 부서 조회
  @Query("SELECT d FROM DepartmentEntity d WHERE d.companyCode = :companyCode")
  List<DepartmentEntity> findByCompanyCode1(@Param("companyCode") String companyCode);

  // 5. 부서 ID로 부서 조회
  DepartmentEntity findByDepartmentId(String departmentId);

  // 6. 회사 코드와 부서 이름으로 부서 조회
  DepartmentEntity findByCompanyCodeAndDepartmentName(String companyCode, String departmentName);
}
