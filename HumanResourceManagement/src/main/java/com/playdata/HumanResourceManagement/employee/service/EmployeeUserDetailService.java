package com.playdata.HumanResourceManagement.employee.service;

import com.playdata.HumanResourceManagement.employee.dto.MyUserDetail;
import com.playdata.HumanResourceManagement.employee.dto.UserDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("userDetailService")
@RequiredArgsConstructor
public class EmployeeUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    // Spring Security에서 사용자 로그인 시 자동으로 호출되는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자 ID로 직원 조회
        Optional<Employee> employeeOpt = employeeRepository.findByEmployeeId(username);
        if (employeeOpt.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        }

        // UserDTO 생성 후 반환
        return createUser(employeeOpt.get());
    }

    // Spring Security의 UserDetails 객체를 생성하는 메서드
    public User createUser(Employee employee) {

        // 직원 권한 리스트를 GrantedAuthority 객체로 변환
        List<GrantedAuthority> authorities = employee.getAuthorityList().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());

        // UserDTO 객체 생성
        UserDTO userDTO = UserDTO.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .email(employee.getEmail()) // 이메일 추가
                .phoneNumber(employee.getPhoneNumber()) // 전화번호 추가
                .departmentId(employee.getDepartmentId()) // 부서 ID 추가
                .state(employee.getStatus()) // 상태 추가
                .authorityList(employee.getAuthorityList()) // 권한 리스트 추가
                .companyCode(employee.getCompany() != null ? employee.getCompany().getCompanyCode() : null)
                .build();

        // MyUserDetail 객체 반환
        return new MyUserDetail(userDTO, authorities);
    }
}
