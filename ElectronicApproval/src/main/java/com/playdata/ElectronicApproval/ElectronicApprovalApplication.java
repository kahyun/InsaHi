package com.playdata.ElectronicApproval;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {
        "com.playdata.User.employee.entity",
        "com.playdata.User.company.entity",
        "com.playdata.ElectronicApproval.entity"
})
@SpringBootApplication
public class ElectronicApprovalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicApprovalApplication.class, args);

    }
}
