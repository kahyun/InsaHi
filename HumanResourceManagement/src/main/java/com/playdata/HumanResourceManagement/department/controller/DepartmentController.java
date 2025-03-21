package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import com.playdata.HumanResourceManagement.department.service.MappingDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/{companyCode}/department")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final MappingDeptService mappingDeptService;
    private final EmployeeDataService employeeDataService;

    @GetMapping("/list")
    public ResponseEntity<List<OrganizationDTO>> getOrganizationChart(@PathVariable String companyCode) {
        // 회사 코드에 해당하는 조직도 데이터 가져오기
        List<OrganizationDTO> organizationChart = mappingDeptService.getOrganizationChart(companyCode);

        if (organizationChart.isEmpty()) {
            log.info("회사 코드 {}에 대한 조직도가 없습니다.", companyCode);
            return ResponseEntity.noContent().build();
        }

        // 직급 및 직원 데이터 가져오기
        List<DownloadPositionDTO> employeeData = employeeDataService.getEmployeeData(companyCode);

        // 직급별 직원 정보 추출
        List<DownloadPositionDTO.PositionWithEmployeesDTO> positionsWithEmployees = employeeData.stream()
                .flatMap(dto -> dto.getPositions().stream())  // 각 DownloadPositionDTO의 positions 리스트를 풀어서 처리
                .collect(Collectors.toList());

        // 조직도 데이터에 직급 및 직원 정보 매핑
        organizationChart.forEach(department -> department.setPositions(positionsWithEmployees));

        log.info("회사 코드 {}에 대한 조직도 및 직급 데이터를 성공적으로 매핑했습니다.", companyCode);
        return ResponseEntity.ok(organizationChart);
    }
}
