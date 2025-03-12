package com.playdata.attendanceSalary.atdSalRepository.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {
    @Query("SELECT COALESCE(SUM(a.overtimeHours), 0) " +
            "FROM AttendanceEntity a " +
            "WHERE a.employeeId = :employeeId " +
            "AND a.workDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalOvertimeHoursByEmployeeAndDateRange(
            @Param("employeeId") String employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
    //sort

}

