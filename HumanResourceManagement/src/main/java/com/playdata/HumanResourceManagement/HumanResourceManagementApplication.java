package com.playdata.HumanResourceManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.playdata.HumanResourceManagement.department.feign")
public class HumanResourceManagementApplication {
  public static void main(String[] args) {
    SpringApplication.run(HumanResourceManagementApplication.class, args);
  }
}

