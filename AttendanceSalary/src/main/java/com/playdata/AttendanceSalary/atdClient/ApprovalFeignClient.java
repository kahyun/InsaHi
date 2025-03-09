package com.playdata.attendanceSalary.atdClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "approval-service", url = "${approval.service.url}")
public interface ApprovalFeignClient {

}