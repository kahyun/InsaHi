package com.playdata.attendanceSalary.atdClient.hrmDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {
    private String employeeId;
    private String employeeName;
    private Long positionId;   // 포지션 ID
    private String companyCode; // 회사 코드
//    private String name;
//    private String role; //enum
//    private String email;
//    private String phoneNumber;
//    private String address;
//    private String gender;
//    private String birthday;
//    private String departmentId;
//    private String teamId;
//    private String state;

}
