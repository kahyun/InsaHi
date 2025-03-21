package com.playdata.HumanResourceManagement.department.feign.Client;

import com.playdata.HumanResourceManagement.department.feign.dto.DownloadPositionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "att", url = "${feign.client.position.url}")
public interface PositionFeign {

    @GetMapping("/api/{companyCode}/position")
    List<DownloadPositionDTO> getPositionsByCompanyCode(@PathVariable String companyCode);
}
