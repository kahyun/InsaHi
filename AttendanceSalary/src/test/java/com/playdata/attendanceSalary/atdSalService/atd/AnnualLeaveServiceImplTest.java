package com.playdata.attendanceSalary.atdSalService.atd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnnualLeaveServiceImplTest {

  @Autowired
  AnnualLeaveServiceImpl annualLeaveService;

  @Test
  public void testAnnualLeave() {
    String employeeId = "E005";
//    annualLeaveService.findAllEmployees();
//    annualLeaveService.findEmployee(employeeId);
    System.out.println(annualLeaveService.findAllEmployees());
    System.out.println(annualLeaveService.findEmployee(employeeId));
  }

}