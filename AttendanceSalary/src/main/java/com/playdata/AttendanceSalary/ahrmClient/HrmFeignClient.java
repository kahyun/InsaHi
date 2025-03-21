package com.playdata.AttendanceSalary.ahrmClient;

import com.playdata.AttendanceSalary.ahrmClient.dto.AttSendPositionDTO;
import com.playdata.AttendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

/**
 * HRM 시스템과 통신하는 Feign Client
 */
@FeignClient(name = "hrm", url = "${hrm.service.url}")
public interface HrmFeignClient {

  /**
   * 직원 ID로 직원 정보 조회
   */
  @GetMapping("/admin/find")
  EmployeeResponseDTO findEmployee(@RequestParam("employeeId") String employeeId);

  /**
   * 회사 코드 기준으로 포지션 리스트 조회
   */
  @GetMapping("/api/{companyCode}/position")
  List<AttSendPositionDTO> getPositionsByCompanyCode(@PathVariable String companyCode);

  /**
   * 회사 코드 및 직급 ID 기준 특정 포지션 조회
   */
  @GetMapping("/api/{companyCode}/position/{positionId}")
  AttSendPositionDTO getPositionById(@PathVariable String companyCode,
                                     @PathVariable Long positionId);

  /**
   * 직원 ID 기준 회사 시작 근무 시간 조회
   */
  @GetMapping("/admin/{employeeId}/company/start-time")
  ResponseEntity<LocalTime> getCompanyStartTime(@PathVariable String employeeId);

  /**
   * 현재 회사 코드 조회
   */
  @GetMapping("/api/companyCode")
  String getCompanyCode();

  /**
   * 회사 포지션 추가
   */
  @PostMapping("/api/{companyCode}/position")
  ResponseEntity<AttSendPositionDTO> addPosition(@PathVariable String companyCode,
                                                 @RequestBody AttSendPositionDTO position);

  /**
   * 회사 포지션 수정
   */
  @PutMapping("/api/{companyCode}/position/{positionId}")
  ResponseEntity<AttSendPositionDTO> updatePosition(@PathVariable String companyCode,
                                                    @PathVariable Long positionId,
                                                    @RequestBody AttSendPositionDTO position);

  /**
   * 회사 포지션 삭제
   */
  @DeleteMapping("/api/{companyCode}/position/{positionId}")
  void deletePosition(@PathVariable String companyCode,
                      @PathVariable Long positionId);

  /**
   * 모든 직원 ID 리스트 반환
   */
  @GetMapping("/admin/employeeid")
  List<String> getEmployeeIds();
}
