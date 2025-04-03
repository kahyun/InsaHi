package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import com.playdata.HumanResourceManagement.department.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyCode}/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    // 직원의 직급 및 급여 정보를 조회
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<OrganizationDTO> getUserWithPositionAndSalary(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("employeeId") String employeeId
    ) {
        return ResponseEntity.ok(positionService.getUserWithPositionAndSalary(employeeId));
    }

    // 특정 회사의 직급 목록 조회
    @GetMapping
    public ResponseEntity<List<PositionDownloadDTO>> getPositionsByCompany(
            @PathVariable("companyCode") String companyCode
    ) {
        return ResponseEntity.ok(positionService.getPositionsByCompany(companyCode));
    }

    // 특정 직원의 직급 상세 정보 조회
    @GetMapping("/employee/{employeeId}/details")
    public ResponseEntity<List<PositionDownloadDTO>> getPositionsByEmployeeId(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("employeeId") String employeeId
    ) {
        return ResponseEntity.ok(positionService.getPositionsByEmployeeId(employeeId));
    }

    // 새로운 직급 추가
    @PostMapping
    public ResponseEntity<PositionDownloadDTO> addPosition(
            @PathVariable("companyCode") String companyCode,
            @RequestBody PositionDownloadDTO positionDTO
    ) {
        return ResponseEntity.ok(positionService.addPosition(companyCode, positionDTO));
    }

    // 특정 직급 삭제
    @DeleteMapping("/{positionName}")
    public ResponseEntity<Boolean> deletePosition(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("positionName") String positionName
    ) {
        return ResponseEntity.ok(positionService.deletePosition(companyCode, positionName));
    }
}
