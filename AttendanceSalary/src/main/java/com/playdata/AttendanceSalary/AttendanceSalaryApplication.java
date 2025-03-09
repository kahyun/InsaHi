package com.playdata.attendanceSalary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableFeignClients  // Feign Client 활성화

public class AttendanceSalaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceSalaryApplication.class, args);
    }
}


