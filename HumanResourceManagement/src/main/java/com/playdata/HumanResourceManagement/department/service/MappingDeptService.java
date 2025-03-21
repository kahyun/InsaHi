package com.playdata.HumanResourceManagement.department.service;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.department.repository.DepartmentRepository;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.repository.EmployeeRepository;
import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MappingDeptService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public List<OrganizationDTO> getOrganizationChart(String companyCode) {
        List<DepartmentEntity> departments = departmentRepository.findByCompanyCode(companyCode);

        return departments.stream()
                .map(department -> mapDepartmentToDTO(department, companyCode))
                .collect(Collectors.toList());
    }

    private OrganizationDTO mapDepartmentToDTO(DepartmentEntity department, String companyCode) {
        List<Employee> employees = employeeRepository.findByDepartment(department);

        Map<String, List<Employee>> groupedByPosition = employees.stream()
                .collect(Collectors.groupingBy(Employee::getPositionName));

        List<DownloadPositionDTO.PositionWithEmployeesDTO> positionWithEmployees = groupedByPosition.entrySet().stream()
                .map(entry -> {
                    List<DownloadPositionDTO.EmployeeDTO> employeeDTOs = entry.getValue().stream()
                            .map(DownloadPositionDTO.EmployeeDTO::fromEmployee)  // EmployeeDTO로 변환
                            .collect(Collectors.toList());
                    return DownloadPositionDTO.PositionWithEmployeesDTO.builder()
                            .positionName(entry.getKey())
                            .employees(employeeDTOs)
                            .build();
                })
                .collect(Collectors.toList());

        return OrganizationDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .positions(positionWithEmployees)
                .companyCode(companyCode)
                .build();
    }
}
