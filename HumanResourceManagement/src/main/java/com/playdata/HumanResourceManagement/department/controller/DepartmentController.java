package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.DepartmentNameDTO;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.CreateDeptService;
import com.playdata.HumanResourceManagement.department.service.DepartmentService;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import com.playdata.HumanResourceManagement.department.service.MappingDeptService;
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

    /** 부서 이름 조회 */
    @GetMapping("/{departmentId}")
    public String getDepartment(@PathVariable String companyCode, @PathVariable String departmentId) {
        try {
            DepartmentNameDTO departmentNameDTO = departmentService.getDepartmentName(companyCode, departmentId);
            return departmentNameDTO.getDepartmentName();
        } catch (Exception e) {
            return "부서 정보를 불러오는 중 오류가 발생했습니다: " + e.getMessage();
        }
    }

    /** 부서 생성 */
    @PostMapping("/create")
    public ResponseEntity<OrganizationDTO> createDepartment(
            @PathVariable String companyCode,
            @RequestBody OrganizationDTO request) {
        OrganizationDTO response = createDeptService.createDepartment(companyCode, request);
        return ResponseEntity.ok(response);
    }

    /** 부서 삭제 */
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<OrganizationDTO> deleteDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId) {
        employeeDataService.moveEmployeesToDefaultDepartment(companyCode, departmentId, "1");
        OrganizationDTO response = createDeptService.deleteDepartment(companyCode, departmentId);
        return ResponseEntity.ok(response);
    }

    /** 부서 이동 */
    @PatchMapping("/move")
    public ResponseEntity<String> moveEmployeesToDepartment(
            @PathVariable String companyCode,
            @RequestParam String fromDepartmentId,
            @RequestParam String toDepartmentId) {
        try {
            employeeDataService.moveEmployeesToDefaultDepartment(companyCode, fromDepartmentId, toDepartmentId);
            return ResponseEntity.ok("직원들이 부서 이동되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("부서 이동 실패: " + e.getMessage());
        }
    }

    /** 조직도 조회 */
    @GetMapping("/list")
    public ResponseEntity<List<OrganizationDTO>> getOrganizationChart(@PathVariable String companyCode) {
        List<OrganizationDTO> organizationChart = mappingDeptService.getOrganizationChart(companyCode);
        organizationChart.forEach(department -> {
            List<UserDataDTO> employees = employeeDataService.getEmployeesByDepartment(companyCode, department.getDepartmentId());
            department.setEmployees(employees);
        });
        return ResponseEntity.ok(organizationChart);
    }

    /** 부서별 직원 목록 조회 */
    @GetMapping("/{departmentId}/list")
    public ResponseEntity<List<UserDataDTO>> getEmployeesByDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId) {
        List<UserDataDTO> employees = employeeDataService.getEmployeesByDepartment(companyCode, departmentId);
        return ResponseEntity.ok(employees);
    }

    /** 전체 직원 목록 조회 */
    @GetMapping("/employees")
    public ResponseEntity<List<UserDataDTO>> getAllEmployees(@PathVariable String companyCode) {
        List<UserDataDTO> allEmployees = employeeDataService.getAllEmployees(companyCode);
        return ResponseEntity.ok(allEmployees);
    }
}
