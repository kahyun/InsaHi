package com.playdata.HumanResourceManagement.department.feign;

import com.playdata.AttendanceSalary.atdClient.hrmDTO.PositionSendDTO;
import com.playdata.HumanResourceManagement.department.dto.PositionDownloadDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "attendance-salary-service")
public interface AttFeignClient {

    @GetMapping("/positions/{companyCode}")
    PositionDownloadDTO getPositionDownloadDTO(@PathVariable String companyCode);

    @PostMapping("/positions/{companyCode}")
    PositionSendDTO addPosition(@PathVariable String companyCode, @RequestBody PositionSendDTO positionDTO);

    @DeleteMapping("/positions/{companyCode}/{positionName}")
    boolean deletePosition(@PathVariable String companyCode, @PathVariable String positionName);
}
