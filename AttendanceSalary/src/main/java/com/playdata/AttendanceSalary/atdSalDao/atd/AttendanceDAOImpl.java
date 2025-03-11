package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AttendanceEntity;
import com.playdata.attendanceSalary.atdSalRepository.atd.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AttendanceDAOImpl implements AttendanceDAO{

    private final AttendanceRepository attendanceRepository;



    @Override
    public AttendanceEntity findById(Long id) {
        return attendanceRepository.findById(id).orElse(null);
    }

    @Override
    public AttendanceEntity save(AttendanceEntity attendance) {
        return attendanceRepository.save(attendance);
    }


    /**
    @Override // 외근 추가
    public AttendanceEntity findWorkingOutside() {
        return attendanceRepository.findWorkingOutside();
    }
    */
}

