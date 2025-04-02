package com.playdata.Chat.feign;

import com.playdata.Chat.config.FeignConfig;
import com.playdata.Chat.dto.EmployeeDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@org.springframework.cloud.openfeign.FeignClient(name = "HumanResourceManagement", url = "http://127.0.0.1:1006", configuration = FeignConfig.class)
public interface EmployeeClient {
    @GetMapping("/employee/all")
    List<EmployeeDTO> getMemberList();
}
