package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /**
     * 새로운 부서를 생성하고 부모 부서를 설정합니다.
     * @param companyCode 회사 코드
     * @param request 부서 생성 데이터
     * @return 생성된 부서 정보
     */
    @Transactional
    public OrganizationDTO createDepartment(String companyCode, OrganizationDTO request) {
        DepartmentEntity parent = getParentDepartmentIfExists(request.getParentDepartmentId());

        // 새로운 부서 생성
        DepartmentEntity department = new DepartmentEntity();
        department.setCompanyCode(companyCode);
        department.setDepartmentName(request.getDepartmentName());
        department.setParentDepartmentId(parent);

        // 부서 ID 생성
        department.setDepartmentId(generateDepartmentId(companyCode));

        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return OrganizationDTO.fromEntity(savedDepartment, companyCode);
    }

    /**
     * 부모 부서를 조회합니다. 부서 계층을 관리합니다.
     * @param parentDepartmentId 부모 부서 ID
     * @return 부모 부서 엔티티
     */
    private DepartmentEntity getParentDepartmentIfExists(String parentDepartmentId) {
        if (parentDepartmentId == null) {
            return null;
        }
        return departmentRepository.findById(parentDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("부모 부서를 찾을 수 없습니다. ID = " + parentDepartmentId));
    }

    /**
     * 부서 ID 생성 로직
     */
    public String generateDepartmentId(String companyCode) {
        List<DepartmentEntity> existingDepartments = departmentRepository.findByCompanyCode1(companyCode);
        int newId = 2; // 0(root), 1(부서 없음) 제외한 값으로 시작

        int finalNewId = newId;
        while (existingDepartments.stream()
                .anyMatch(dept -> dept.getDepartmentId().equals(companyCode + finalNewId))) {
            newId++; // 중복된 ID가 있으면 증가
        }

        return companyCode + newId;
    }

    /**
     * 부모 부서를 기준으로 하위 부서를 추가합니다.
     * @param parentDepartmentId 부모 부서 ID
     * @param request 하위 부서 데이터
     * @return 추가된 하위 부서 정보
     */
    @Transactional
    public OrganizationDTO addSubDepartment(String parentDepartmentId, OrganizationDTO request) {
        DepartmentEntity parentDepartment = departmentRepository.findById(parentDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("부모 부서를 찾을 수 없습니다. ID = " + parentDepartmentId));

        // 하위 부서 생성
        DepartmentEntity subDepartment = new DepartmentEntity();
        subDepartment.setCompanyCode(parentDepartment.getCompanyCode());
        subDepartment.setDepartmentName(request.getDepartmentName());
        subDepartment.setParentDepartmentId(parentDepartment);

        // 부서 ID 생성
        subDepartment.setDepartmentId(generateDepartmentId(parentDepartment.getCompanyCode()));

        DepartmentEntity savedSubDepartment = departmentRepository.save(subDepartment);

        return OrganizationDTO.fromEntity(savedSubDepartment, parentDepartment.getCompanyCode());
    }

    /**
     * 부서 삭제
     * @param companyCode 회사 코드
     * @param departmentId 삭제할 부서 ID
     * @return 삭제된 부서 정보
     */
    @Transactional
    public OrganizationDTO deleteDepartment(String companyCode, String departmentId) {
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new EntityNotFoundException("부서 ID가 없습니다. ID = " + departmentId));

        // 하위 부서들을 찾고, 부모 부서를 null로 설정
        List<DepartmentEntity> subDepartments = departmentRepository.findByParentDepartmentId(department);

        for (DepartmentEntity subDept : subDepartments) {
            subDept.setParentDepartmentId(null); // 하위 부서의 부모 부서 설정을 null로 변경
            departmentRepository.save(subDept); // 하위 부서 저장
        }

        departmentRepository.delete(department); // 부서 삭제

        return OrganizationDTO.fromEntity(department, companyCode);
    }
}
