package com.playdata.AttendanceSalary.atdClient;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.CompanyStartTimeDto;
import com.playdata.AttendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "hrm", url = "${hrm.service.url}", configuration = FeignClientConfig.class)
public interface HrmFeignClient {

  /**
   * 특정 직원 정보를 조회
   * @param employeeId 직원 ID
   * @return EmployeeResponseDTO 직원 정보
   */
  @GetMapping("/api/employee/{employeeId}")
  EmployeeResponseDTO findEmployee(@RequestParam("employeeId") String employeeId);
  @GetMapping(value = "/employee/find", produces = {MediaType.APPLICATION_JSON_VALUE,
      MediaType.TEXT_HTML_VALUE})
  EmployeeResponseDTO findEmployee(@RequestParam("employeeId") String employeeId);

  /**
   * 직급 데이터를 HRM 시스템으로 전송
   * @param companyCode 회사 코드
   * @param positionDTOs 직급 데이터 리스트
   */
  @PostMapping("/api/{companyCode}/position/list")
  void sendPositionsToHRM(@RequestParam("companyCode") String companyCode,
                          @RequestBody List<PositionSendDTO> positionDTOs);

  /**
   * 회사 코드에 해당하는 직급 리스트를 조회
   * @param companyCode 회사 코드
   * @return 직급 데이터 리스트
   */
  @GetMapping("/api/{companyCode}/position/list")
  List<PositionSendDTO> getPositions(@RequestParam("companyCode") String companyCode);
  @GetMapping("/employee/{employeeId}/company/start-time")
  ResponseEntity<LocalTime> getCompanyStartTime(
      @PathVariable("employeeId") String employeeId);

  /**
   * 특정 직원의 회사 시작 시간을 조회
   * @param employeeId 직원 ID
   * @return 회사 시작 시간 정보
   */
  @GetMapping("/api/employee/{employeeId}/startTime")
  ResponseEntity<LocalTime> getCompanyStartTime(@PathVariable("employeeId") String employeeId);

  /**
   * 모든 직원 ID를 조회
   * @return 직원 ID 리스트
   */
  @GetMapping("/api/employees/ids")
  List<String> getEmployeeIds();
  @GetMapping("/employee/getallemployeeids")
  List<String> getEmployeeIds();

  /**
   * 회사 시작 시간 정보 추가
   * @param companyStartTimeDto 회사 시작 시간 정보
   * @return 추가된 회사 시작 시간 정보
   */
  @PostMapping("/company/start-Time")
  CompanyStartTimeDto insertStartTime(@RequestBody CompanyStartTimeDto companyStartTimeDto);
}
  @PostMapping("/company/start-Time")
  CompanyStartTimeDto insertStartTime(@RequestBody CompanyStartTimeDto companyStartTimeDto);


}