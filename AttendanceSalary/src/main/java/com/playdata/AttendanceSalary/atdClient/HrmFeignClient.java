package com.playdata.attendanceSalary.atdClient;
import com.playdata.attendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.LocalTime;

@FeignClient(name = "hrm-service", url = "${hrm.service.url}")
public interface HrmFeignClient {

    @GetMapping("/admin/find")
    EmployeeResponseDTO findEmployee(@RequestParam("employeeId") String employeeId);


    @GetMapping("/admin/{employeeId}/company/start-time")
    public ResponseEntity<LocalTime> getCompanyStartTime(@PathVariable("employeeId") String employeeId);
}