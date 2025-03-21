package com.playdata.AttendanceSalary.ahrmClient.controller;

import com.playdata.AttendanceSalary.ahrmClient.dto.AttSendPositionDTO;
import com.playdata.AttendanceSalary.ahrmClient.service.AttPositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 직급(Position) 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/{companyCode}/position")
@RequiredArgsConstructor
public class PositionFeignController {

    private final AttPositionService attPositionService;

    @GetMapping
    public ResponseEntity<List<AttSendPositionDTO>> getPositionsByCompanyCode(@PathVariable String companyCode) {
        List<AttSendPositionDTO> positions = attPositionService.getPositionsByCompanyCode(companyCode);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttSendPositionDTO> getPositionById(@PathVariable String companyCode,
                                                              @PathVariable Long id) {
        AttSendPositionDTO position = attPositionService.getPositionById(companyCode, id);
        return ResponseEntity.ok(position);
    }
}
