package com.playdata.HumanResourceManagement.employee.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseAtdSalDTO {

  private String employeeId;
  private String role;
  private String companyCode;
  private Long positionSalaryId;
  private LocalDate hireDate;

}
