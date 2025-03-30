package com.playdata.HumanResourceManagement.department.service.UserAddService.user;

import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@Transactional
public class RoleUser {

    private final UserDataDAO userDataDAO;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public RoleUser(UserDataDAO userDataDAO, PasswordEncoder passwordEncoder, DepartmentRepository departmentRepository) {
        this.userDataDAO = userDataDAO;
        this.passwordEncoder = passwordEncoder;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeResponseDTO addUser(RequestUserDataDTO requestUserDataDTO) {
        // 직원이 이미 존재하는지 확인 (Validation)
        if (userDataDAO.existsByEmployeeId(requestUserDataDTO.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with this ID already exists");
        }

        // Employee 엔티티 생성
        Employee employee = createOrUpdateEmployeeFromDTO(requestUserDataDTO);

        // 직원 저장 후, Response DTO 반환
        Employee savedEmployee = userDataDAO.save(employee);
        return new EmployeeResponseDTO(savedEmployee);
    }

    public void deleteUser(String employeeId) {
        if (!userDataDAO.existsByEmployeeId(employeeId)) {
            throw new IllegalArgumentException("Employee with this ID does not exist");
        }
        userDataDAO.deleteByEmployeeId(employeeId);
    }

    public EmployeeResponseDTO updateUser(String employeeId, RequestUserDataDTO updatedInfo) {
        Optional<Employee> employeeOptional = userDataDAO.findByEmployeeId(employeeId);

        if (!employeeOptional.isPresent()) {
            throw new IllegalArgumentException("Employee with this ID does not exist");
        }

        Employee employee = employeeOptional.get();
        createOrUpdateEmployeeFromDTO(updatedInfo, employee);

        // 업데이트된 직원 정보 저장 후 Response DTO 반환
        Employee updatedEmployee = userDataDAO.save(employee);
        return new EmployeeResponseDTO(updatedEmployee);
    }

    public EmployeeResponseDTO getUserById(String employeeId) {
        Optional<Employee> employeeOptional = userDataDAO.findByEmployeeId(employeeId);

        if (!employeeOptional.isPresent()) {
            throw new IllegalArgumentException("Employee with this ID does not exist");
        }

        return new EmployeeResponseDTO(employeeOptional.get());
    }

    private Employee createOrUpdateEmployeeFromDTO(RequestUserDataDTO dto) {
        Employee employee = new Employee();
        return createOrUpdateEmployeeFromDTO(dto, employee);
    }

    private Employee createOrUpdateEmployeeFromDTO(RequestUserDataDTO dto, Employee employee) {
        employee.setEmployeeId(dto.getEmployeeId() != null ? dto.getEmployeeId() : UUID.randomUUID().toString()); // UUID로 ID 생성
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setPhoneNumber(dto.getPhoneNumber());
        employee.setAddress(dto.getAddress());
        employee.setGender(dto.getGender());
        employee.setBirthday(dto.getBirthday()); // 날짜 처리 개선
        DepartmentEntity department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다."));
        employee.setDepartment(department); // DepartmentEntity 설정
        employee.setState(dto.getState());
        employee.setHireDate(dto.getHireDate());
        employee.setRetireDate(dto.getRetireDate());
        employee.setPositionName(dto.getPositionName());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            employee.setPassword(encodedPassword);
        }

        return employee;
    }
}
