package com.playdata.attendanceSalary.atdSalRepository.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnualLeaveUsageRepository extends JpaRepository<AnnualLeaveUsageEntity,Long> {
}
