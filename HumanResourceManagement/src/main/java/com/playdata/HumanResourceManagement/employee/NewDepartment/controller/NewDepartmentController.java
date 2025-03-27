package com.playdata.HumanResourceManagement.employee.NewDepartment.controller;


import com.playdata.HumanResourceManagement.employee.NewDepartment.dto.ActionBasedOrganizationChartDTO;
import com.playdata.HumanResourceManagement.employee.NewDepartment.dto.FullOrganizationChartDTO;
import com.playdata.HumanResourceManagement.employee.NewDepartment.dto.OrganizationStructureDTO;
import com.playdata.HumanResourceManagement.employee.NewDepartment.service.NewCreateDeptService;
import com.playdata.HumanResourceManagement.employee.NewDepartment.service.NewMappingDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor

public class NewDepartmentController {

  private final NewCreateDeptService createDeptService;
  private final NewMappingDeptService mappingDeptService;

  /**
   * 조직도 리스트 조회 (캐시 또는 DB 조회)
   */
  @GetMapping("{companyCode}/list")
  public ResponseEntity<List<FullOrganizationChartDTO>> getOrganizationChart(
      @PathVariable("companyCode") String companyCode) {
    // Redis 사용 없이 DB에서 직접 가져오는 부분으로 변경
    List<FullOrganizationChartDTO> organizationChart = mappingDeptService.getOrganizationChart(
        companyCode);

    if (organizationChart.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(organizationChart);
  }

  /**
   * 부서 생성
   */
  @PostMapping("{companyCode}/create")
  public ResponseEntity<ActionBasedOrganizationChartDTO> createDepartment(
      @PathVariable String companyCode,
      @RequestBody OrganizationStructureDTO request) {

    ActionBasedOrganizationChartDTO createdDepartment = createDeptService.createDepartment(
        companyCode, request);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
  }

  /**
   * 부서 수정
   */
  @PutMapping("/edit/{departmentId}")
  public ResponseEntity<ActionBasedOrganizationChartDTO> editDepartment(
      @PathVariable String companyCode,
      @PathVariable String departmentId,
      @RequestBody OrganizationStructureDTO request) {

    ActionBasedOrganizationChartDTO updatedDepartment = createDeptService.updateDepartment(
        companyCode, departmentId, request);

    return ResponseEntity.ok(updatedDepartment);
  }

  /**
   * 부서 삭제
   */
  @DeleteMapping("/delete/{departmentId}")
  public ResponseEntity<Void> deleteDepartment(
      @PathVariable String companyCode,
      @PathVariable String departmentId) {

    ActionBasedOrganizationChartDTO deletedDepartment = createDeptService.deleteDepartment(
        companyCode, departmentId);

    return ResponseEntity.noContent().build();
  }
}
