package com.playdata.AttendanceSalary.atdClient.hrmDTO;

import lombok.Data;
import java.time.LocalTime;

@Data
public class CompanyStartTimeDto {
    private String companyCode;
    private LocalTime startTime;

    public LocalTime getBody() {
        return startTime;
    }
}
