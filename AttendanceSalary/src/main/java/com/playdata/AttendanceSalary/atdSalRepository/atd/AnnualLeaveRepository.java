package com.playdata.attendanceSalary.atdSalRepository.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity,Long> {
}
