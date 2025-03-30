package com.playdata.HumanResourceManagement.department.service;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.feign.AttFeignClient;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 직급 및 급여 정보를 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class PositionService {

    private final AttFeignClient attFeignClient;
    private final EmployeeRepository employeeRepository;

    /**
     * 직원 ID 기준으로 직급 및 급여 정보를 가져와 사용자 정보와 결합
     *
     * @param employeeId 직원 ID
     * @return OrganizationDTO (직급 급여 정보 포함)
     */
    public OrganizationDTO getUserWithPositionAndSalary(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        List<PositionDownloadDTO> positions = getPositionsFromAttFeignClient(employee);

        UserDataDTO userDataDTO = mapToUserDataDTO(employee);

        return OrganizationDTO.fromEntityAndUserData(null, List.of(userDataDTO), positions);
    }

    /**
     * 직원 엔티티를 UserDataDTO로 변환하는 메서드
     *
     * @param employee 직원 엔티티
     * @return 변환된 UserDataDTO
     */
    private UserDataDTO mapToUserDataDTO(Employee employee) {
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
                employee.getHireDate() != null ? employee.getHireDate() : LocalDate.parse("2000-01-01"),  // 입사일 (기본값 설정)
                employee.getRetireDate() != null ? employee.getRetireDate() : LocalDate.parse("-")  // 퇴사일 (기본값 설정)
        );
    }

    /**
     * 외부 API 호출을 통해 직원의 직급 정보를 가져오는 메서드
     *
     * @param employee 직원 엔티티
     * @return 직급 목록 (하나만 반환)
     */
    private List<PositionDownloadDTO> getPositionsFromAttFeignClient(Employee employee) {
        // 직급이 하나만 반환되도록 필터링
        return Optional.ofNullable(attFeignClient.getPositionDownloadDTO(employee.getCompanyCode()))
                .map(PositionDownloadDTO::getPositions)
                .orElse(List.of())
                .stream()
                .filter(position -> position.getEmployeeId().equals(employee.getEmployeeId()))
                .limit(1)  // 직원당 하나의 직급만 반환
                .collect(Collectors.toList());
    }

    /**
     * 회사 코드로 직급 목록 조회
     *
     * @param companyCode 회사 코드
     * @return 직급 목록
     */
    public List<PositionSendDTO> getPositionsByCompany(String companyCode) {
        PositionDownloadDTO positionDownloadDTO = attFeignClient.getPositionDownloadDTO(companyCode);
        if (positionDownloadDTO == null || positionDownloadDTO.getPositions() == null) {
            return List.of();
        }

        // PositionDownloadDTO를 PositionSendDTO로 변환하여 반환
        return positionDownloadDTO.getPositions().stream()
                .map(this::convertToPositionSendDTO)
                .collect(Collectors.toList());
    }

    /**
     * 직원 ID 기준으로 직급 정보 조회
     *
     * @param employeeId 직원 ID
     * @return 직급 목록 (하나만 반환)
     */
    public List<PositionSendDTO> getPositionsByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("직원 정보가 존재하지 않습니다."));

        // 직급은 하나만 가져오도록 수정
        List<PositionDownloadDTO> positions = getPositionsFromAttFeignClient(employee);

        // 직급이 존재하면 하나만 반환
        return positions.isEmpty() ? List.of() : List.of(convertToPositionSendDTO(positions.get(0)));
    }

    /**
     * 직급 정보 추가
     *
     * @param companyCode 회사 코드
     * @param positionDTO 직급 정보
     * @return 추가된 직급 정보
     */
    public PositionSendDTO addPosition(String companyCode, PositionSendDTO positionDTO) {
        return attFeignClient.addPosition(companyCode, positionDTO);
    }

    /**
     * 직급 삭제
     *
     * @param companyCode 회사 코드
     * @param positionName 직급명
     * @return 삭제 성공 여부
     */
    public boolean deletePosition(String companyCode, String positionName) {
        return attFeignClient.deletePosition(companyCode, positionName);
    }

    /**
     * PositionDownloadDTO를 PositionSendDTO로 변환하는 메서드
     *
     * @param positionDownloadDTO 직급 정보
     * @return 변환된 PositionSendDTO
     */
    private PositionSendDTO convertToPositionSendDTO(PositionDownloadDTO positionDownloadDTO) {
        return PositionSendDTO.builder()
                .positionSalaryId(positionDownloadDTO.getPositionSalaryId())
                .positionName(positionDownloadDTO.getPositionName())
                .salaryStepId(positionDownloadDTO.getSalaryStepId())
                .build();
    }
}
