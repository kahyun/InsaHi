package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.feign.Client.PositionFeign;
import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDataService {

    private final EmployeeRepository employeeRepository;
    private final PositionFeign positionFeign;

    public List<DownloadPositionDTO> getEmployeeData(String companyCode) {
        // 직원 데이터 가져오기
        List<Employee> employees = employeeRepository.findByCompany_CompanyCode(companyCode);

        // 직급 정보 가져오기
        List<DownloadPositionDTO> positionData = positionFeign.getPositionsByCompanyCode(companyCode);

        // 직원과 직급 정보를 매핑하여 반환
        return positionData.stream()
                .map(position -> {
                    List<DownloadPositionDTO.EmployeeDTO> employeeDTOs = employees.stream()
                            .filter(employee -> employee.getPositionId().equals(position.getPositionId()))
                            .map(DownloadPositionDTO.EmployeeDTO::fromEmployee)
                            .collect(Collectors.toList());

                    // DownloadPositionDTO 객체를 생성하고, 직급 및 직원 정보 포함
                    return DownloadPositionDTO.builder()
                            .companyCode(companyCode)
                            .positions(List.of(
                                    DownloadPositionDTO.PositionWithEmployeesDTO.builder()
                                            .positionId(position.getPositionId())
                                            .positionName(position.getPositionName())
                                            .salaryStep(position.getSalaryStep())
                                            .employees(employeeDTOs)
                                            .build()
                            ))
                            .build();
                })
                .collect(Collectors.toList());
    }
}
