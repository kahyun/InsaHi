package com.playdata.HumanResourceManagement.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmallProfileDTO {
    private String name;
    private String phoneNumber;
    private String departmentId;
    private String positionSalaryId;


}
