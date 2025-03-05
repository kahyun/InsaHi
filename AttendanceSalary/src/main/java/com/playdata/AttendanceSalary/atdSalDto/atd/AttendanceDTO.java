package com.playdata.attendanceSalary.atdSalDto.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class AttendanceDTO {
    private final Long id;
//    private final String employeeId;
    private final LocalDateTime checkInTime;


    public AttendanceDTO(AttendanceEntity attendance) {
        this.id = attendance.getId();
        //this.employeeId = attendance.getEmployeeId();
        this.checkInTime = attendance.getCheckInTime();
    }
}
