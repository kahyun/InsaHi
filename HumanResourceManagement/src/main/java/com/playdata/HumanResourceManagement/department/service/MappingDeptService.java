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
        List<DepartmentEntity> departments = departmentRepository.findByCompanyCode(companyCode);

        return departments.stream()
                .map(department -> {
                    // 부서 ID와 이름을 매핑한 DTO 생성
                    OrganizationDTO orgDTO = OrganizationDTO.builder()
                            .departmentId(department.getDepartmentId())
                            .departmentName(department.getDepartmentName())
                            .companyCode(department.getCompanyCode())
                            .build();

                    // 해당 부서에 속한 직원 목록 조회
                    List<UserDataDTO> employees = getEmployeesInDepartment(department);

                    // 직원 목록을 포함한 조직도 DTO 반환
                    orgDTO.setEmployees(employees);
                    return orgDTO;
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
        List<Employee> employees = employeeRepository.findByDepartmentId(department.getDepartmentId());

        // 직원 데이터를 UserDataDTO로 변환하여 반환
        return employees.stream()
                .map(this::convertToUserDataDTO)
                .collect(Collectors.toList());
    }

    /**
     * 직원 엔티티를 UserDataDTO로 변환하는 메서드
     *
     * @param employee 직원 엔티티
     * @return 변환된 UserDataDTO
     */
    private UserDataDTO convertToUserDataDTO(Employee employee) {
        return new UserDataDTO(
                employee.getEmployeeId(),
                employee.getPassword(),
                employee.getName(),
                employee.getRole(),
                employee.getCompanyCode(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getAddress(),  // 주소
                employee.getGender(),   // 성별
                employee.getBirthday(), // 생일
                employee.getDepartmentId(),
                employee.getStatus(),   // 상태 (Active, Inactive 등)
                employee.getHireDate(),
                employee.getRetireDate(),
                employee.getPositionName()  // 직급명 추가
        );
    }

}
