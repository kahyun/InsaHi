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
public class CreateDeptService {

    private final DepartmentRepository departmentRepository;

    /**
     * 부서 생성 (계층 처리 포함)
     *
     * @param companyCode 회사 코드
     * @param request     부서 생성에 필요한 데이터
     * @return 생성된 부서 정보
     */
    @Transactional
    public OrganizationDTO createDepartment(String companyCode, OrganizationDTO request) {
        // 부모 부서가 있다면 가져오기
        DepartmentEntity parent = getParentDepartmentIfExists(request.getParentDepartmentId());

        // 새로운 부서 생성
        DepartmentEntity department = DepartmentEntity.builder()
                .companyCode(companyCode)
                .departmentName(request.getDepartmentName())
                .parentDepartmentId(parent)  // 부모 부서 정보 연결
                .build();

        // 부서 저장
        DepartmentEntity savedDepartment = departmentRepository.save(department);

        // 저장된 부서 정보를 DTO로 변환하여 반환
        return OrganizationDTO.fromEntity(savedDepartment, companyCode);
    }

    /**
     * 부모 부서를 조회하는 메서드 (부서 계층 관리)
     *
     * @param parentDepartmentId 부모 부서 ID
     * @return 부모 부서 엔티티
     */
    private DepartmentEntity getParentDepartmentIfExists(String parentDepartmentId) {
        if (parentDepartmentId == null) {
            return null; // 부모 부서가 없으면 null 처리
        }
        return departmentRepository.findById(parentDepartmentId)
                .orElseThrow(() -> new EntityNotFoundException("부모 부서를 찾을 수 없습니다. ID = " + parentDepartmentId));
    }

    /**
     * 부서 삭제
     *
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
