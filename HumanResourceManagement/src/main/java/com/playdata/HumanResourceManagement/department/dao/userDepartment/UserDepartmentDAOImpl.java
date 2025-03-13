package com.playdata.HumanResourceManagement.department.dao.userDepartment;

import com.playdata.HumanResourceManagement.department.dto.department.DepartmentRequest;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDepartmentDAOImpl implements UserDepartmentDAO {

    //private final DepartmentRepository departmentRepository;

    // 1. 회사 코드로 부서+사용자 리스트 조회
    @Override
    public List<DepartmentEntity> getDepartmentsByCompanyCode(String companyCode) {
        return null;// departmentRepository.findByCompanyCode(companyCode);
    }

    // 2-1. 부서 업데이트
    @Override
    public DepartmentEntity updateDepartment(String departmentId, DepartmentRequest departmentRequest) {
//        Optional<DepartmentEntity> departmentOptional = departmentRepository.findById(departmentId);
//        if (departmentOptional.isPresent()) {
//            DepartmentEntity department = departmentOptional.get();
//            //department.setDepartmentName(departmentRequest.getDepartmentName());
//            //department.setParentDepartmentId(departmentRequest.getParentDepartmentId());
//            //department.setDepartmentLevel(departmentRequest.getDepartmentLevel());
//            return departmentRepository.save(department);
//        }
        return null;
    }

    // 2-2. 사원 업데이트 (부서 이동 포함)
    @Override
    public DepartmentEntity updateUserDepartment(String departmentId, DepartmentRequest departmentRequest) {
//        Optional<DepartmentEntity> departmentOptional = departmentRepository.findById(departmentId);
//        if (departmentOptional.isPresent()) {
//            DepartmentEntity department = departmentOptional.get();
//          //  department.setCompanyCode(departmentRequest.getCompanyCode());
//            return departmentRepository.save(department);
//        }
        return null;
    }

    // 3. 부서 삭제 (사용자는 삭제 불가)
    @Override
    public void deleteDepartment(String companyCode, String departmentId) {
//        Optional<DepartmentEntity> departmentOptional = departmentRepository.findById(departmentId);
//        if (departmentOptional.isPresent() && departmentOptional.get().getCompanyCode().equals(companyCode)) {
//            departmentRepository.deleteById(departmentId);
//        }
    }
}
