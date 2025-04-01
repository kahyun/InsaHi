package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.DepartmentNameDTO;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.CreateDeptService;
import com.playdata.HumanResourceManagement.department.service.DepartmentService;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import com.playdata.HumanResourceManagement.department.service.MappingDeptService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyCode}/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final EmployeeDataService employeeDataService;
    private final CreateDeptService createDeptService;
    private final MappingDeptService mappingDeptService;

    private final DepartmentService departmentService;



    /**
     * 부서 이름을 반환하는 API
     * @param companyCode 회사 코드
     * @param departmentId 부서 ID
     * @return 부서 이름
     */
    @GetMapping("/{departmentId}")
    public String getDepartment(@PathVariable String companyCode, @PathVariable String departmentId) {
        try {
            DepartmentNameDTO departmentNameDTO = departmentService.getDepartmentName(companyCode, departmentId);
            return departmentNameDTO.getDepartmentName();
        } catch (Exception e) {
            return "부서 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage();
        }
    }



    /**
     * 부서 생성
     *
     * @param companyCode 회사 코드
     * @param request     부서 생성에 필요한 데이터
     * @return 생성된 부서 정보
     */
    @PostMapping
    public ResponseEntity<OrganizationDTO> createDepartment(
            @PathVariable String companyCode,
            @RequestBody OrganizationDTO request) {
        OrganizationDTO response = createDeptService.createDepartment(companyCode, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 부서 삭제
     *
     * @param companyCode  회사 코드
     * @param departmentId 삭제할 부서 ID
     * @return 삭제된 부서 정보
     */
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<OrganizationDTO> deleteDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId) {
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

        // 조직도에 부서별 직원 목록 추가
        organizationChart.forEach(department -> {
            List<UserDataDTO> employees = employeeDataService.getEmployeesByDepartment(companyCode, department.getDepartmentId());
            department.setEmployees(employees);  // 부서에 직원 목록 설정
        });

        return ResponseEntity.ok(organizationChart);
    }

    /**
     * 부서별 직원 목록 조회
     *
     * @param companyCode  회사 코드
     * @param departmentId 부서 ID
     * @return 해당 부서의 직원 리스트
     */
    @GetMapping("/{departmentId}/list")
    public ResponseEntity<List<UserDataDTO>> getEmployeesByDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId) {
        List<UserDataDTO> employees = employeeDataService.getEmployeesByDepartment(companyCode, departmentId);
        return ResponseEntity.ok(employees);
    }

    /**
     * 전체 직원 목록 조회
     *
     * @param companyCode 회사 코드
     * @return 회사의 모든 직원 리스트
     */
    @GetMapping("/employees")
    public ResponseEntity<List<UserDataDTO>> getAllEmployees(@PathVariable String companyCode) {
        List<UserDataDTO> allEmployees = employeeDataService.getAllEmployees(companyCode);
        return ResponseEntity.ok(allEmployees);
    }
}
