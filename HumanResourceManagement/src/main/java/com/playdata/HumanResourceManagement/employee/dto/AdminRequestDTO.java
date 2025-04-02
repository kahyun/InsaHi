package com.playdata.HumanResourceManagement.employee.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDTO {

  private String employeeId;
  private String password;
  private String name;
  private String companyCode;
  private String departmentId;
  private String email;
  private String phoneNumber;
  private Date hireDate;

}
