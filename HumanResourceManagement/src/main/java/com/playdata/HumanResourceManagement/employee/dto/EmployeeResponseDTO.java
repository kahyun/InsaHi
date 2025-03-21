package com.playdata.HumanResourceManagement.employee.dto;


import com.playdata.AttendanceSalary.atdSalDto.sal.PositionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String employeeId;
    private String password;
    private String name;
    private String role;
    private String companyCode;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String birthday;
    private String departmentId;
    private String teamId;
    private String state;
    private String positionSalaryId;

    // 직급 정보 추가
    private PositionResponseDTO position;


}
