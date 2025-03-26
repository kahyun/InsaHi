package com.playdata.HumanResourceManagement.department.service;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.feign.AttFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final AttFeignClient attFeignClient;

    /**
     * 회사 코드와 직급 정보를 받아 직원을 직급과 함께 조회합니다.
     *
     * @param companyCode      회사 코드
     * @param positionSendDTOs 직급 정보 DTO 목록
     * @return 직급과 함께 매핑된 직원 목록
     */
    public List<OrganizationDTO> getEmployeesWithPosition(String companyCode, List<PositionSendDTO> positionSendDTOs) {
        // 회사 코드에 해당하는 직원 목록 조회
        List<Employee> employees = employeeRepository.findByCompany_CompanyCode(companyCode);

        return employees.stream()
                .map(employee -> {
                    // 직원 정보 추출
                    UserDataDTO userDataDTO = convertToUserDataDTO(employee);

                    // 직급 정보 추출
                    List<PositionDownloadDTO> positions = getPositionsFromAttFeignClient(employee);

                    // OrganizationDTO 반환 (부서 정보는 null로 처리)
                    return OrganizationDTO.fromEntityAndUserData(null, List.of(userDataDTO), positions);
                })
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
                employee.getName(),
                employee.getPositionName(),
                employee.getEmail(),
                employee.getPhoneNumber(),  // 전화번호 추가
                employee.getAddress(),       // 주소 추가
                employee.getGender(),        // 성별 추가
                employee.getBirthday(),      // 생년월일 추가
                employee.getDepartmentId(),  // 부서 ID 추가
                employee.getStatus(),        // 상태 추가
                employee.getHireDate(),      // 입사일 추가
                employee.getRetireDate()     // 퇴사일 추가
        );
    }

    /**
     * ATT 시스템에서 직급 정보를 가져오는 메서드
     *
     * @param employee 직원 엔티티
     * @return 직급 정보 리스트
     */
    private List<PositionDownloadDTO> getPositionsFromAttFeignClient(Employee employee) {
        PositionDownloadDTO positionDownloadDTO = attFeignClient.getPositionDownloadDTO(employee.getCompanyCode());

        // 직급 정보 필터링
        return positionDownloadDTO.getPositions().stream()
                .filter(position -> position.getEmployeeId().equals(employee.getEmployeeId())) // 직원 ID로 필터링
                .collect(Collectors.toList());
    }

    /**
     * 사용자 정보 추가
     *
     * @param userDTO 사용자 정보
     * @return 추가된 사용자 정보
     */
    public UserDataDTO addUser(UserDataDTO userDTO) {
        Employee employee = new Employee();
        // userDTO의 데이터를 employee 엔티티로 변환하여 저장하는 로직 (DB 저장 로직은 예시로 생략)
        return userDTO;
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

    /**
     * 회사 코드로 직원 목록 조회 (DB에서 불러오기)
     *
     * @param companyCode 회사 코드
     * @return 직원 목록
     */
    public List<UserDataDTO> getAllEmployees(String companyCode) {
        List<Employee> employees = employeeRepository.findByCompany_CompanyCode(companyCode);

        return employees.stream()
                .map(emp -> new UserDataDTO(
                        emp.getEmployeeId(),
                        emp.getName(),
                        emp.getPositionName(),
                        emp.getEmail(),
                        emp.getPhoneNumber(),  // 전화번호 추가
                        emp.getAddress(),       // 주소 추가
                        emp.getGender(),        // 성별 추가
                        emp.getBirthday(),      // 생년월일 추가
                        emp.getDepartmentId(),  // 부서 ID 추가
                        emp.getStatus(),        // 상태 추가
                        emp.getHireDate(),      // 입사일 추가
                        emp.getRetireDate()     // 퇴사일 추가
                ))
                .collect(Collectors.toList());
    }
}
