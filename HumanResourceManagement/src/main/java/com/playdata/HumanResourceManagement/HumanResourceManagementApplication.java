package com.playdata.HumanResourceManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EntityScan(basePackages = {"com.playdata.HumanResourceManagement", "com.playdata.AttendanceSalary"})
public class HumanResourceManagementApplication {
  public static void main(String[] args) {
    SpringApplication.run(HumanResourceManagementApplication.class, args);
  }
}
