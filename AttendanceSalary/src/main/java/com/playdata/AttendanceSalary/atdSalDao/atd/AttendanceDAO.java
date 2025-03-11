package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import org.springframework.stereotype.Repository;


@Repository
public interface AttendanceDAO {
    AttendanceEntity save(AttendanceEntity attendance);
    AttendanceEntity findById(Long id);



}

