package com.playdata.HumanResourceManagement.department.feign.service;

import com.playdata.HumanResourceManagement.department.feign.Client.PositionFeign;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
// EmployeeDTO 변환 로직을 서비스에서 관리
@Service
@RequiredArgsConstructor
public class DownloadPositionService {

    private final PositionFeign positionFeign;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public DownloadPositionDTO getPositionsWithEmployees(String companyCode) {

        // 외부 API에서 직급 정보 조회
        List<DownloadPositionDTO> positionsFromAtt = positionFeign.getPositionsByCompanyCode(companyCode);

        // 직급별 직원 목록 생성
        List<DownloadPositionDTO.PositionWithEmployeesDTO> positionsWithEmployees = positionsFromAtt.stream()
                .map(position -> {
                    List<Employee> employeesInPosition = employeeRepository.findByCompany_CompanyCodeAndPositionName(companyCode, position.getPositionName());
                    List<DownloadPositionDTO.EmployeeDTO> employeeDTOs = employeesInPosition.stream()
                            .map(employee -> modelMapper.map(employee, DownloadPositionDTO.EmployeeDTO.class))
                            .collect(Collectors.toList());

                    return DownloadPositionDTO.PositionWithEmployeesDTO.builder()
                            .positionName(position.getPositionName())
                            .employees(employeeDTOs)
                            .build();
                })
                .collect(Collectors.toList());

        return DownloadPositionDTO.builder()
                .companyCode(companyCode)
                .positions(positionsWithEmployees)
                .build();
    }
}
