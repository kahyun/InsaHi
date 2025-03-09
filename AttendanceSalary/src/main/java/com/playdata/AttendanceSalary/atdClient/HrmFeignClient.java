package com.playdata.attendanceSalary.atdClient;
import com.playdata.attendanceSalary.atdClient.hrmDTO.EmployeeResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hrm-service", url = "${hrm.service.url}")
public interface HrmFeignClient {
//
//    @GetMapping
//    EmployeeResponseDTO getEmployee(@PathVariable("id") String employeeId,@RequestParam String companyCode);

}