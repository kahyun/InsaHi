package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;


@Repository
public interface AttendanceDAO {
    AttendanceEntity save(AttendanceEntity attendance);
    AttendanceEntity findById(Long id);
    BigDecimal getTotalOvertimeHoursByEmployeeAndDateRange(String employeeId, LocalDate startDate, LocalDate endDate);



}

