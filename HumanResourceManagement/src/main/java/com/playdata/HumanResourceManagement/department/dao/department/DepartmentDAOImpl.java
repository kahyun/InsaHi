package com.playdata.HumanResourceManagement.department.dao.department;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DepartmentDAOImpl implements DepartmentDAO {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<DepartmentEntity> findByCompanyCode(String companyCode) {
        return departmentRepository.findByCompanyCode(companyCode);
    }

    @Override
    public Optional<DepartmentEntity> findById(String departmentId) {
        return departmentRepository.findById(departmentId);
    }

    @Override
    public DepartmentEntity save(DepartmentEntity department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteById(String departmentId) {
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public boolean existsByDepartmentId(String departmentId) {
                //employeeRepository.existsByDepartmentId(departmentId);
        return departmentRepository.existsById(departmentId);
    }
}
