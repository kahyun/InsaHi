package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.authentication.EmpAuthenticationToken;
import com.playdata.HumanResourceManagement.employee.dao.AuthorityDAO;
import com.playdata.HumanResourceManagement.employee.dao.EmployeeDAO;
import com.playdata.HumanResourceManagement.employee.dto.AdminRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.AuthorityResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmpAuthResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeUpdateDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.dto.ProfileCardDTO;
import com.playdata.HumanResourceManagement.employee.dto.UpdatePasswordDTO;
import com.playdata.HumanResourceManagement.employee.entity.Authority;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeDAO employeeDAO;
  private final AuthorityDAO authorityDAO;
  private final ModelMapper modelMapper;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final PasswordEncoder passwordEncoder;
  private final EmployeeRepository employeeRepository;


  @Override
  public List<EmpAuthResponseDTO> findByAuthorityList_AuthorityName(String authorityName) {
    List<Employee> employees = employeeRepository.findByAuthorityList_AuthorityName(authorityName);
    return employees.stream().map(e -> modelMapper.
            map(e, EmpAuthResponseDTO.class))
        .collect(Collectors.toList());
  }

  @Override
  public void addAdminRoleToEmployee(String employeeId) {
    employeeDAO.addAdminRoleToEmployee(employeeId);
  }

  @Override
  public void removeAdminRoleFromEmployee(String employeeId) {
    employeeDAO.removeAdminRoleFromEmployee(employeeId);
  }

  @Override
  public List<AuthorityResponseDTO> findAuthoritiesByCompanyCode(String companyCode) {
    List<Authority> authorities = employeeDAO.findAuthoritiesByCompanyCode(companyCode);
    return authorities.stream().map(
        authority -> modelMapper
            .map(authority, AuthorityResponseDTO.class)).collect(Collectors.toList());
  }

  @Override
  public Employee adminInsert(AdminRequestDTO adminRequestDTO) {

    Employee entity = modelMapper.map(adminRequestDTO, Employee.class);

    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    employeeDAO.insert(entity);
    return entity;
  }

  // ROLE_ADMIN + ROLE_USER 권한 부여 (DAO 활용)
  @Override
  public void addAdminAndUserRoles(Employee employee) {
    Set<Authority> roles = new HashSet<>();

    // DAO에서 ROLE_ADMIN, ROLE_USER 가져오기
    authorityDAO.getAdminRole().ifPresent(roles::add);
    authorityDAO.getUserRole().ifPresent(roles::add);

    // Employee에 권한 추가
    employee.setAuthorityList(roles);
    employeeDAO.insert(employee);
  }


  @Override
  public Authentication login(LoginDTO employee) {
    //스프링시큐리티의 인증이 실행되도록 처리

    // 1. 커스텀 인증 토큰 (EmpAuthenticationToken) 생성
    EmpAuthenticationToken token =
        new EmpAuthenticationToken(
            employee.getEmployeeId(),
            employee.getPassword(),
            employee.getCompanyCode()
        );

    // 2. Spring Security의 인증 시스템을 사용하여 인증 수행
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);

    return authentication;

  }

  @Override
  public EmployeeResponseDTO findEmployeeById(String employeeId) {
    Employee employee = employeeDAO.findById(employeeId);
    return modelMapper.map(employee, EmployeeResponseDTO.class);
  }

  @Override
  /// 김다울 추가
  public LocalTime findCompanyStartTimeByEmployeeId(String employeeId) {
    return employeeDAO.findCompanyStartTimeByEmployeeId(employeeId);
  }


  @Override
  public List<String> getAllEmployeeIds() {
    return employeeDAO.findAll().stream()
        .map(employee -> employee.getEmployeeId())
        .collect(Collectors.toList());


  }

  //mypage 왼쪽 작은 프로필
  @Override
  public ProfileCardDTO getProfileCard(String employeeId) {
    Employee employee = employeeDAO.findByEmployeeId(employeeId);
    ProfileCardDTO ProfileCardDTO = modelMapper.map(employee, ProfileCardDTO.class);

    return ProfileCardDTO;
  }

  //개인정보수정페이지
  @Override
  public EmployeeResponseDTO getEmployeeInfo(String employeeId) {
    Employee employee = employeeDAO.findByEmployeeId(employeeId);
    EmployeeResponseDTO employeeResponseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);
    return employeeResponseDTO;
  }

  //개인정보 변경
  @Override
  public EmployeeResponseDTO updateEmployeeInfo(String employeeId,
      EmployeeUpdateDTO employeeUpdateDTO) {
    Employee employee = employeeDAO.findById(employeeId);
    modelMapper.map(employeeUpdateDTO, employee);
    employeeDAO.update(employee);
    EmployeeResponseDTO employeeResponseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);

    return employeeResponseDTO;
  }

  //비밀번호 변경
  @Override
  public EmployeeResponseDTO updatePassword(String employeeId,
      UpdatePasswordDTO updatePasswordDTO) {
    Employee employee = employeeDAO.findById(employeeId);
    if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), employee.getPassword())) {
      throw new IllegalArgumentException("현재 비밀번호가 올바르지 않습니다.");
    }
    String encodedNewPassword = passwordEncoder.encode(updatePasswordDTO.getNewPassword());
    employee.setPassword(encodedNewPassword);

    employeeDAO.update(employee);
    EmployeeResponseDTO employeeResponseDTO = modelMapper.map(employee, EmployeeResponseDTO.class);
    return employeeResponseDTO;
  }

  //user 권한만 추가
  @Override
  public void addUserRoles(Employee employee) {
    Set<Authority> roles = new HashSet<>();

    authorityDAO.getUserRole().ifPresent(roles::add);

    employee.setAuthorityList(roles);
    employeeDAO.insert(employee);
  }

  //회원 등록
  @Override
  public Employee employeeInsert(EmployeeRequestDTO employeeRequestDTO) {
    Employee entity = modelMapper.map(employeeRequestDTO, Employee.class);

    entity.setPassword(passwordEncoder.encode(entity.getPassword()));
    employeeDAO.insert(entity);
    return entity;
  }

  @Override
  public List<Employee> getMemberList() {
    return employeeRepository.findAll();
  }

}

