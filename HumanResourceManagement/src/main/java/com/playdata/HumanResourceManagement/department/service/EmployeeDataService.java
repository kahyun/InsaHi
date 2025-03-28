package com.playdata.HumanResourceManagement.department.service;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.company.repository.CompanyRepository;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.entity.Authority;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.feign.AttFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final AttFeignClient attFeignClient;
    private final CompanyRepository companyRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 직원 엔티티를 UserDataDTO로 변환하는 메서드
     *
     * @param employee 직원 엔티티
     * @return 변환된 UserDataDTO
     */
    private UserDataDTO convertToUserDataDTO(Employee employee) {
        return new UserDataDTO(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getPositionName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getAddress(),
                employee.getGender(),
                employee.getBirthday(),
                employee.getDepartmentId(),
                employee.getStatus(),
                employee.getHireDate(),
                employee.getRetireDate()
        );
    }

    /**
     * 회사 코드로 직원 목록 조회 (DB에서 불러오기)
     *
     * @param companyCode 회사 코드
     * @return 직원 목록
     */
    public List<UserDataDTO> getAllEmployees(String companyCode) {
        // 회사 코드로 모든 직원을 조회
        List<Employee> employees = employeeRepository.findByCompany_CompanyCode(companyCode);

        // Employee 목록을 UserDataDTO로 변환 후 반환
        return employees.stream()
                .map(this::convertToUserDataDTO)
                .collect(Collectors.toList());
    }

    /**
     * 부서 ID와 회사 코드로 직원 목록 조회
     *
     * @param companyCode 회사 코드
     * @param departmentId 부서 코드
     * @return 직원 목록
     */
    public List<UserDataDTO> getEmployeesByDepartment(String companyCode, String departmentId) {
        // 회사 코드로 모든 직원을 조회한 후, 부서 ID로 필터링
        List<Employee> employees = employeeRepository.findByCompany_CompanyCode(companyCode);

        // 부서 ID가 일치하는 직원들만 필터링
        List<Employee> filteredEmployees = employees.stream()
                .filter(emp -> emp.getDepartment() != null && emp.getDepartment().getDepartmentId().equals(departmentId))
                .collect(Collectors.toList());

        // 필터링된 직원 목록을 UserDataDTO로 변환 후 반환
        return filteredEmployees.stream()
                .map(this::convertToUserDataDTO)
                .collect(Collectors.toList());
    }

    /**
     * 사용자 정보 추가
     *
     * @param userDTO 사용자 정보
     * @return 추가된 사용자 정보
     */
    public UserDataDTO create(UserDataDTO userDTO) {
        // UserDataDTO를 Employee 엔티티로 변환
        Employee employee = new Employee();
        employee.setEmployeeId(userDTO.getEmployeeId());
        employee.setName(userDTO.getName());

        // 권한 설정
        Authority authority = new Authority();
        authority.setAuthorityName(userDTO.getRole());
        employee.setAuthorityList(new HashSet<>(Set.of(authority)));

        // 회사 코드
        Company company = companyRepository.findByCompanyCode1(userDTO.getCompanyCode());
        employee.setCompany(company);

        // 부서 코드
        DepartmentEntity department = departmentRepository.findByDepartmentId(userDTO.getDepartmentId());
        employee.setDepartment(department);

        // 나머지 사용자 정보 설정
        employee.setEmail(userDTO.getEmail());
        employee.setPhoneNumber(userDTO.getPhoneNumber());
        employee.setAddress(userDTO.getAddress());
        employee.setGender(userDTO.getGender());
        employee.setBirthday(userDTO.getBirthday());
        employee.setState(userDTO.getState());
        employee.setHireDate(userDTO.getHireDate());
        employee.setRetireDate(userDTO.getRetireDate());
        employee.setPositionName(userDTO.getPositionName());

        // 저장
        Employee savedEmployee = employeeRepository.save(employee);

        // 저장된 Employee 엔티티를 UserDataDTO로 변환하여 반환
        return new UserDataDTO(
                savedEmployee.getEmployeeId(),
                savedEmployee.getName(),
                savedEmployee.getPositionName(),
                savedEmployee.getEmail(),
                savedEmployee.getPhoneNumber(),
                savedEmployee.getAddress(),
                savedEmployee.getGender(),
                savedEmployee.getBirthday(),
                savedEmployee.getDepartmentId(),
                savedEmployee.getStatus(),
                savedEmployee.getHireDate(),
                savedEmployee.getRetireDate()
        );
    }

    /**
     * 사용자 삭제
     *
     * @param employeeId 삭제할 사용자 ID
     * @return 삭제된 사용자 정보
     */
    public UserDataDTO deleteUser(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        employeeRepository.delete(employee);
        return convertToUserDataDTO(employee);
    }
}
