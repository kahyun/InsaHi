package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.service.CreateDeptService;
import com.playdata.HumanResourceManagement.department.service.MappingDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyCode}/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final CreateDeptService createDeptService;
    private final MappingDeptService mappingDeptService;

    /**
     * 부서 생성
     *
     * @param request 부서 생성에 필요한 데이터
     * @return 생성된 부서 정보
     */
    @PostMapping
    public ResponseEntity<OrganizationDTO> createDepartment(@PathVariable String companyCode, @RequestBody OrganizationDTO request) {
        OrganizationDTO response = createDeptService.createDepartment(companyCode, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 부서 삭제
     *
     * @param companyCode 회사 코드
     * @param departmentId 삭제할 부서 ID
     * @return 삭제된 부서 정보
     */
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<OrganizationDTO> deleteDepartment(@PathVariable String companyCode, @PathVariable String departmentId) {
        OrganizationDTO response = createDeptService.deleteDepartment(companyCode, departmentId);
        return ResponseEntity.ok(response);
    }

    /**
     * 조직도 조회
     *
     * @param companyCode 회사 코드
     * @return 회사의 모든 부서 정보 포함 조직도
     */
    @GetMapping("/list")
    public ResponseEntity<List<OrganizationDTO>> getOrganizationChart(@PathVariable String companyCode) {
        List<OrganizationDTO> organizationChart = mappingDeptService.getOrganizationChart(companyCode);
        return ResponseEntity.ok(organizationChart);
    }
}
