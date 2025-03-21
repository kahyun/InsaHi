package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateDeptService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public OrganizationDTO createDepartment(String companyCode, OrganizationDTO request) {
        // 부모 부서 정보 확인
        DepartmentEntity parent = getParentDepartmentIfExists(request.getParentDepartmentId());

        // 부서 생성
        DepartmentEntity department = DepartmentEntity.builder()
                .companyCode(companyCode)
                .departmentName(request.getDepartmentName())
                .parentDepartmentId(parent)
                .build();
        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return OrganizationDTO.fromEntity(savedDepartment, null, "CREATE", companyCode);
    }

    private DepartmentEntity getParentDepartmentIfExists(String parentDepartmentId) {
        if (parentDepartmentId == null) {
            return null;
        }
        return departmentRepository.findById(parentDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("부모 부서를 찾을 수 없습니다. ID = " + parentDepartmentId));
    }

    @Transactional
    public OrganizationDTO deleteDepartment(String companyCode, String departmentId) {
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("부서 ID가 없습니다. ID = " + departmentId));
        departmentRepository.delete(department);
        return OrganizationDTO.fromEntity(department, null, "DELETE", companyCode);
    }
}
