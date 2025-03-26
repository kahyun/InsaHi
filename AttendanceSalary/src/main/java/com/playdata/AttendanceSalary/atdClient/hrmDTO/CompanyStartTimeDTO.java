package com.playdata.AttendanceSalary.atdClient.hrmDTO;

import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
public class CompanyStartTimeDTO {
    private String startTime;

    /**
     * startTime을 LocalTime으로 변환해서 반환
     * @return LocalTime 객체
     */
    public LocalTime getBody() {
        // startTime이 "HH:mm" 형식이라고 가정하고 LocalTime으로 변환
        if (startTime != null && !startTime.isEmpty()) {
            return LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm"));
        }
        return null; // 변환할 수 없으면 null 반환
    }

}
