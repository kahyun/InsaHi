package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.department.DepartmentRequest;
import com.playdata.HumanResourceManagement.department.dto.department.DepartmentResponse;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    // 특정 회사의 모든 부서 조회
    public List<DepartmentResponse> getDepartmentsByCompanyCode(String companyCode) {
        return departmentRepository.findByCompanyCode(companyCode).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 부서 생성
    @Transactional
    public DepartmentResponse createDepartment(String companyCode, DepartmentRequest request) {
        DepartmentEntity departmentEntity = new DepartmentEntity(
                null,
                request.getDepartmentName(),
                request.getParentDepartmentId(),
                request.getDepartmentLevel(),
                companyCode,
                request.getLeftNode(),
                request.getRightNode()
        );

        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);
        return convertToResponse(savedDepartment);
    }

    // 부서 수정
    @Transactional
    public DepartmentResponse updateDepartment(String companyCode, String departmentId, DepartmentRequest request) {
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        department.setCompanyCode(companyCode);
        department.setDepartmentName(request.getDepartmentName());

        return convertToResponse(departmentRepository.save(department));
    }

    // 부서 삭제 (팀원이 없을 경우만 가능)
    @Transactional
    public void deleteDepartment(String companyCode, String departmentId) {
        DepartmentEntity department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("부서를 찾을 수 없습니다."));

        if (!department.getCompanyCode().equals(companyCode)) {
            throw new RuntimeException("부서가 해당 회사에 속해 있지 않습니다.");
        }
//
//        if (employeeRepository.existsByDepartmentId(departmentId)) {
//            throw new RuntimeException("부서에 소속된 직원이 있어 삭제할 수 없습니다.");
//        }

        departmentRepository.deleteById(departmentId);
    }

    private DepartmentResponse convertToResponse(DepartmentEntity entity) {
        return new DepartmentResponse(
                entity.getDepartmentId(),
                entity.getCompanyCode(),
                entity.getDepartmentName(),
                entity.getParentDepartmentId(),
                entity.getDepartmentLevel(),
                entity.getLeftNode(),
                entity.getRightNode()
        );
    }
}
