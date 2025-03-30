package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        List<Employee> employees = employeeRepository.findByCompanyCode(companyCode);
        return employees.stream()
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
        List<Employee> employees = employeeRepository.findByCompanyCodeAndDepartmentId(companyCode, departmentId);
        return employees.stream()
                .map(UserDataDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 회사 코드와 직원 ID로 직원 조회
     *
     * @param companyCode  회사 코드
     * @param employeeId   직원 ID
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
     * @param companyCode  회사 코드
     * @param employeeId   직원 ID
     * @return 삭제된 직원
     */
    public UserDataDTO deleteUser(String companyCode, String employeeId) {
        Employee employee = employeeRepository.findByCompanyCodeAndEmployeeId(companyCode, employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

        employeeRepository.delete(employee);

        return UserDataDTO.fromEntity(employee); // 삭제된 직원을 UserDataDTO로 반환
    }

    /**
     * 직원 정보 수정
     *
     * @param companyCode  회사 코드
     * @param employeeId   직원 ID
     * @param userDTO      수정된 직원 정보
     * @return 수정된 직원
     */
    public UserDataDTO updateUser(String companyCode, String employeeId, UserDataDTO userDTO) {
        Employee employee = employeeRepository.findByCompanyCodeAndEmployeeId(companyCode, employeeId)
                .orElseThrow(() -> new IllegalArgumentException("직원을 찾을 수 없습니다."));

        // 부서 정보 가져오기
        var department = departmentRepository.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        // 수정할 직원 정보를 적용
        employee.setName(userDTO.getName());
        employee.setPosition(userDTO.getPositionName());
        employee.setDepartment(department); // 부서 객체 할당

        employeeRepository.save(employee); // 수정된 직원 저장

        return UserDataDTO.fromEntity(employee); // 수정된 직원 반환
    }

    public UserDataDTO createUser(UserDataDTO userDTO) {
        // 부서 정보 가져오기
        var department = departmentRepository.findById(userDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));

        // 새로운 직원 객체 생성
        Employee employee = new Employee();
        employee.setEmployeeId(UUID.randomUUID().toString());
        employee.setName(userDTO.getName());
        employee.setEmail(userDTO.getEmail());
        employee.setPhoneNumber(userDTO.getPhoneNumber());
        employee.setAddress(userDTO.getAddress());
        employee.setGender(userDTO.getGender());
        employee.setState(userDTO.getState());
        employee.setPositionName(userDTO.getPositionName());
        employee.setPositionSalaryId(userDTO.getPositionSalaryId());
        employee.setSalaryStepId(userDTO.getSalaryStepId());
        employee.setHireDate(userDTO.getHireDate());
        employee.setRetireDate(userDTO.getRetireDate());
        employee.setBirthday(userDTO.getBirthday());
        employee.setCompany(department.getCompany()); // 회사는 부서에 속해 있으므로 부서에서 가져옴
        employee.setDepartment(department); // 부서 정보 설정
        employee.setAuthorityList(new HashSet<>()); // 권한 목록 초기화 (추후 설정 필요)

        // 기본 비밀번호 설정
        employee.setPassword("defaultPassword");

        // 직원 저장
        employeeRepository.save(employee);

        // 저장된 직원을 UserDataDTO로 반환
        return UserDataDTO.fromEntity(employee);
    }

}
