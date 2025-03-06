package com.playdata.HumanResourceManagement.department.dao.userDepartment;

import com.playdata.HumanResourceManagement.department.dto.department.DepartmentRequest;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDepartmentDAO {

    // 1. 회사 코드로 부서+사용자 리스트 조회
    List<DepartmentEntity> getDepartmentsByCompanyCode(String companyCode);

    // 2. 업데이트 ------------------------------------------------------------------------------

        // 2-1. 부서 업데이트
        DepartmentEntity updateDepartment(String departmentId, DepartmentRequest departmentRequest);
        // 2-2. 사원 업데이트
        DepartmentEntity updateUserDepartment(String departmentId, DepartmentRequest departmentRequest);

    // 3. 삭제 ※ 부서만 삭제 가능, 사용자는 삭제 안 됨 ------------------------------------------------------------------------------
        void deleteDepartment(String companyCode, String departmentId);
}
