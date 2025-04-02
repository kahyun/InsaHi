package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MappingDeptService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    /**
     * 회사 코드로 모든 부서와 해당 부서에 속한 직원 목록 조회
     *
     * @param companyCode 회사 코드
     * @return 회사의 모든 부서와 해당 부서에 속한 직원 목록
     */
    public List<OrganizationDTO> getOrganizationChart(String companyCode) {
        // 부서 조회
        List<DepartmentEntity> departments = departmentRepository.findByCompanyCode1(companyCode);
        System.out.println("Found " + departments.size() + " departments for company code: " + companyCode);

        // 부서에 대해 매핑
        return departments.stream()
                .map(department -> {
                    // 부서 이름과 ID 출력
                    System.out.println("Processing Department: " + department.getDepartmentName());

                    // 하위 부서 목록 처리
                    List<OrganizationDTO> subDepartments = department.getSubDepartments().stream()
                            .map(subDept -> OrganizationDTO.fromEntity(subDept, companyCode)) // companyCode 추가
                            .collect(Collectors.toList());

                    System.out.println("Found " + subDepartments.size() + " sub-departments for " + department.getDepartmentName());

                    // 부서의 직원 목록 처리
                    List<UserDataDTO> employees = getEmployeesInDepartment(department);
                    System.out.println("Found " + employees.size() + " employees in department: " + department.getDepartmentName());

                    // OrganizationDTO 반환
                    return OrganizationDTO.builder()
                            .departmentId(department.getDepartmentId())
                            .departmentName(department.getDepartmentName())
                            .companyCode(department.getCompanyCode())
                            .parentDepartmentId(department.getParentDepartmentId() != null ? department.getParentDepartmentId().getDepartmentId() : null)
                            .subDepartments(subDepartments)  // 하위 부서 처리
                            .employees(employees)  // 직원 데이터 추가
                            .build();
                })
                .collect(Collectors.toList());
    }

    /**
     * 부서에 속한 직원 목록을 조회하는 메서드
     *
     * @param department 부서 엔티티
     * @return 부서에 속한 직원 목록
     */
    private List<UserDataDTO> getEmployeesInDepartment(DepartmentEntity department) {
        // 부서에 속한 직원들 조회
        List<Employee> employees = employeeRepository.findByDepartmentId(department.getDepartmentId());
        return employees.stream()
                .map(employee -> UserDataDTO.fromEntity(employee))
                .collect(Collectors.toList());
    }
}
