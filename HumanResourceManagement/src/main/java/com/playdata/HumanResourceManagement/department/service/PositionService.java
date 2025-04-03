package com.playdata.HumanResourceManagement.department.service;

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

    public OrganizationDTO getUserWithPositionAndSalary(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("사용자 정보가 존재하지 않습니다."));

        List<PositionDownloadDTO> positions = getPositionsFromAttFeignClient(employee);
        UserDataDTO userDataDTO = mapToUserDataDTO(employee, positions);
        return OrganizationDTO.fromEntityAndUserData(null, List.of(userDataDTO), positions);
    }

    private UserDataDTO mapToUserDataDTO(Employee employee, List<PositionDownloadDTO> positions) {
        String positionName = positions.isEmpty() ? "미지정" : positions.get(0).getPositionName();

        return new UserDataDTO(
                employee.getEmployeeId(),
                employee.getName(),
                positionName,
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDepartmentId(),
                employee.getStatus(),
                Optional.ofNullable(employee.getHireDate()).orElse(LocalDate.parse("2000-01-01")),
                Optional.ofNullable(employee.getRetireDate()).orElse(LocalDate.parse("-")),
                employee.getPositionSalaryId()
        );
    }

    private List<PositionDownloadDTO> getPositionsFromAttFeignClient(Employee employee) {
        return Optional.ofNullable(attFeignClient.getPositionDownloadDTO(employee.getCompanyCode()))
                .map(dto -> Optional.ofNullable(dto.getPositions()).orElse(List.of()))
                .orElse(List.of())
                .stream()
                .filter(position -> employee.getEmployeeId().equals(position.getEmployeeId()))
                .limit(1)
                .collect(Collectors.toList());
    }

    public List<PositionDownloadDTO> getPositionsByCompany(String companyCode) {
        return Optional.ofNullable(attFeignClient.getPositionDownloadDTO(companyCode))
                .map(dto -> Optional.ofNullable(dto.getPositions()).orElse(List.of()))
                .orElse(List.of())
                .stream()
                .map(this::convertToPositionSendDTO)
                .collect(Collectors.toList());
    }

    public List<PositionDownloadDTO> getPositionsByEmployeeId(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("직원 정보가 존재하지 않습니다."));
        List<PositionDownloadDTO> positions = getPositionsFromAttFeignClient(employee);
        return positions.isEmpty() ? List.of() : List.of(convertToPositionSendDTO(positions.get(0)));
    }

    public PositionDownloadDTO addPosition(String companyCode, PositionDownloadDTO positionDTO) {
        return attFeignClient.addPosition(companyCode, positionDTO);
    }

    public boolean deletePosition(String companyCode, String positionName) {
        return attFeignClient.deletePosition(companyCode, positionName);
    }

    private PositionDownloadDTO convertToPositionSendDTO(PositionDownloadDTO positionDownloadDTO) {
        return PositionDownloadDTO.builder()
                .positionSalaryId(positionDownloadDTO.getPositionSalaryId())
                .positionName(positionDownloadDTO.getPositionName())
                .salaryStepId(positionDownloadDTO.getSalaryStepId())
                .build();
    }
}
