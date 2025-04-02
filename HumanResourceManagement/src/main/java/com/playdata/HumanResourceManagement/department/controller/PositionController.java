package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.HumanResourceManagement.department.dto.OrganizationDTO;
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

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<OrganizationDTO> getUserWithPositionAndSalary(@PathVariable String employeeId) {
        return ResponseEntity.ok(positionService.getUserWithPositionAndSalary(employeeId));
    }

    @GetMapping("/company")
    public ResponseEntity<List<PositionSendDTO>> getPositionsByCompany(@PathVariable String companyCode) {
        return ResponseEntity.ok(positionService.getPositionsByCompany(companyCode));
    }

    @GetMapping("/employee/{employeeId}/details")
    public ResponseEntity<List<PositionSendDTO>> getPositionsByEmployeeId(@PathVariable String employeeId) {
        return ResponseEntity.ok(positionService.getPositionsByEmployeeId(employeeId));
    }

    @PostMapping
    public ResponseEntity<PositionSendDTO> addPosition(
            @PathVariable String companyCode,
            @RequestBody PositionSendDTO positionDTO
    ) {
        return ResponseEntity.ok(positionService.addPosition(companyCode, positionDTO));
    }

    @DeleteMapping("/{positionName}")
    public ResponseEntity<Boolean> deletePosition(
            @PathVariable String companyCode,
            @PathVariable String positionName
    ) {
        return ResponseEntity.ok(positionService.deletePosition(companyCode, positionName));
    }
}
