package com.playdata.attendanceSalary.atdClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "attendance-service", url = "${attendance.service.url")
public interface AttendanceFeignClient {

}