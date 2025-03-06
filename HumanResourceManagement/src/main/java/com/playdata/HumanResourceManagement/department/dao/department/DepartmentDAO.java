package com.playdata.HumanResourceManagement.department.dao.department;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import java.util.List;
import java.util.Optional;

public interface DepartmentDAO {
    List<DepartmentEntity> findByCompanyCode(String companyCode);
    Optional<DepartmentEntity> findById(String departmentId);
    DepartmentEntity save(DepartmentEntity department);
    void deleteById(String departmentId);
    boolean existsByDepartmentId(String departmentId);
}
