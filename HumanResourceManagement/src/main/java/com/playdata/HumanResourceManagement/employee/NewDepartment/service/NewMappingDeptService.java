package com.playdata.HumanResourceManagement.employee.NewDepartment.service;

import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.NewDepartment.dto.EmployeeDataDTO;
import com.playdata.HumanResourceManagement.employee.NewDepartment.dto.FullOrganizationChartDTO;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewMappingDeptService {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;

  /**
   * 조직도 전체 조회
   */
  @Transactional(readOnly = true)
  public List<FullOrganizationChartDTO> getOrganizationChart(String companyCode) {
    return departmentRepository.findAllByCompanyCode(companyCode).stream()
            .filter(dept -> dept instanceof DepartmentEntity && ((DepartmentEntity) dept).getParentDepartmentId() == null) // 타입 체크 후 필터링
            .map(dept -> buildHierarchy((DepartmentEntity) dept)) // dept를 DepartmentEntity로 캐스팅
            .collect(Collectors.toList());
  }

  /**
   * 재귀적으로 부서 계층 생성
   */
  private FullOrganizationChartDTO buildHierarchy(DepartmentEntity department) {
    // 부서의 직원 데이터를 조회
    List<EmployeeDataDTO> employees = findEmployeesByDepartment(department.getDepartmentId());

    // 부서 정보와 직원 리스트를 바탕으로 DTO 생성
    FullOrganizationChartDTO response = FullOrganizationChartDTO.fromEntity(department, employees,
        null);

    // 서브 부서가 존재할 경우 재귀적으로 처리
    if (department.getSubDepartments() != null && !department.getSubDepartments().isEmpty()) {
      department.getSubDepartments().forEach(subDept -> {
        response.getSubDepartments().add(buildHierarchy(subDept));  // 서브 부서를 추가
      });
    }

    return response;
  }

  /**
   * 특정 부서의 직원 조회
   */
  @Transactional(readOnly = true)
  public List<EmployeeDataDTO> findEmployeesByDepartment(String departmentId) {
    return employeeRepository.findByDepartment_DepartmentId(departmentId).stream()
        .map(EmployeeDataDTO::fromEntity)
        .collect(Collectors.toList());
  }
}
