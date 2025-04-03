package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    /**
     * 회사의 모든 직원 목록 조회
     *
     * @param companyCode 회사 코드
     * @return 회사의 모든 직원 리스트
     */
    public List<UserDataDTO> getAllEmployees(String companyCode) {
        return employeeRepository.findByCompanyCode(companyCode).stream()
                .map(UserDataDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 부서별 직원 목록 조회
     *
     * @param companyCode  회사 코드
     * @param departmentId 부서 ID
     * @return 부서에 속한 직원 목록
     */
    public List<UserDataDTO> getEmployeesByDepartment(String companyCode, String departmentId) {
        return employeeRepository.findByCompanyCodeAndDepartmentId(companyCode, departmentId).stream()
                .map(UserDataDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 회사 코드와 직원 ID로 직원 조회
     *
     * @param companyCode 회사 코드
     * @param employeeId  직원 ID
     * @return 직원 정보
     */
    public UserDataDTO getEmployeeByCompanyCodeAndId(String companyCode, String employeeId) {
        Employee employee = employeeRepository.findByCompanyCodeAndEmployeeId(companyCode, employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));
        return UserDataDTO.fromEntity(employee);
    }

    /**
     * 직원 삭제
     *
     * @param companyCode 회사 코드
     * @param employeeId  직원 ID
     * @return 삭제된 직원 정보
     */
    @Transactional
    public UserDataDTO deleteUser(String companyCode, String employeeId) {
        Employee employee = employeeRepository.findByCompanyCodeAndEmployeeId(companyCode, employeeId)
                .orElse(null);

        if (employee == null) {
            return null; // 직원이 존재하지 않으면 null 반환
        }

        employeeRepository.delete(employee);
        return UserDataDTO.fromEntity(employee);
    }

    /**
     * 직원 정보 수정
     *
     * @param companyCode 회사 코드
     * @param employeeId  직원 ID
     * @param userDTO     수정된 직원 정보
     * @return 수정된 직원 정보
     */
    @Transactional
    public UserDataDTO updateUser(String companyCode, String employeeId, UserDataDTO userDTO) {
        Employee employee = employeeRepository.findByCompanyCodeAndEmployeeId(companyCode, employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

        var department = departmentRepository.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        employee.setName(userDTO.getName());
        employee.setPosition(userDTO.getPositionName());
        employee.setDepartment(department);

        employeeRepository.save(employee);
        return UserDataDTO.fromEntity(employee);
    }

    /**
     * 직원 생성
     *
     * @param userDTO 직원 정보
     * @return 생성된 직원 정보
     */
    @Transactional
    public UserDataDTO createUser(UserDataDTO userDTO) {
        var department = departmentRepository.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setName(userDTO.getName());
        employee.setEmail(userDTO.getEmail());
        employee.setPhoneNumber(userDTO.getPhoneNumber());
        employee.setState(userDTO.getState());
        employee.setPositionSalaryId(userDTO.getPositionSalaryId());
        employee.setHireDate(userDTO.getHireDate());
        employee.setRetireDate(userDTO.getRetireDate());
        employee.setCompany(department.getCompany());
        employee.setDepartment(department);
        employee.setAuthorityList(new HashSet<>()); // 권한 목록 초기화
        employee.setPassword("defaultPassword");

        employeeRepository.save(employee);
        return UserDataDTO.fromEntity(employee);
    }

    /**
     * 특정 부서의 모든 직원을 새로운 부서로 이동
     *
     * @param companyCode      회사 코드
     * @param fromDepartmentId 기존 부서 ID
     * @param toDepartmentId   이동할 부서 ID
     */
    @Transactional
    public void moveEmployeesToDefaultDepartment(String companyCode, String fromDepartmentId, String toDepartmentId) {
        List<Employee> employees = employeeRepository.findByCompanyCodeAndDepartmentId(companyCode, fromDepartmentId);

        employees.forEach(employee -> employee.setDepartmentId(toDepartmentId));

        employeeRepository.saveAll(employees);
    }
}
