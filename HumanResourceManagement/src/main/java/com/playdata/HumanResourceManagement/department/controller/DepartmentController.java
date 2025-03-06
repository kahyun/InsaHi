package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.department.DepartmentRequest;
import com.playdata.HumanResourceManagement.department.dto.department.DepartmentResponse;
import com.playdata.HumanResourceManagement.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 특정 회사의 모든 부서 조회
    @GetMapping("/{companyCode}/list")
    public ResponseEntity<List<DepartmentResponse>> getDepartments(@PathVariable String companyCode) {
        return ResponseEntity.ok(departmentService.getDepartmentsByCompanyCode(companyCode));
    }

    // 새로운 부서 추가
    @PostMapping("/{companyCode}/create")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @PathVariable String companyCode,
            @RequestBody DepartmentRequest departmentRequest
    ) {
        return ResponseEntity.ok(departmentService.createDepartment(companyCode, departmentRequest));
    }

    // 부서 정보 수정 (이름 및 기타 변경)
    @PutMapping("/{companyCode}/edit/{departmentId}")
    public ResponseEntity<DepartmentResponse> editDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId,
            @RequestBody DepartmentRequest departmentRequest
    ) {
        return ResponseEntity.ok(departmentService.updateDepartment(companyCode, departmentId, departmentRequest));
    }

    // 부서 삭제 (팀원이 없을 경우만 삭제 가능)
    @DeleteMapping("/{companyCode}/delete/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable String companyCode,
            @PathVariable String departmentId
    ) {
        departmentService.deleteDepartment(companyCode, departmentId);
        return ResponseEntity.noContent().build();
    }
}
