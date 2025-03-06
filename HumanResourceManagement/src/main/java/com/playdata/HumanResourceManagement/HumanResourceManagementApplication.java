package com.playdata.HumanResourceManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {
        "com.playdata.HumanResourceManagement.employee.entity",
        "com.playdata.HumanResourceManagement.company.entity",
        "com.playdata.HumanResourceManagement.department.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.playdata.HumanResourceManagement.company.repository",
        "com.playdata.HumanResourceManagement.employee.repository",
        "com.playdata.HumanResourceManagement.department.repository"
})
@SpringBootApplication
public class HumanResourceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanResourceManagementApplication.class, args);

    }
}
