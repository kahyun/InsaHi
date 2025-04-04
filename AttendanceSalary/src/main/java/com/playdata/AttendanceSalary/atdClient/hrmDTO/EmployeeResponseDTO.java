package com.playdata.AttendanceSalary.atdClient.hrmDTO;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

  private String employeeId;
  private String role;
  private String companyCode;
  private Long positionSalaryId;
  private Date hireDate;


}

