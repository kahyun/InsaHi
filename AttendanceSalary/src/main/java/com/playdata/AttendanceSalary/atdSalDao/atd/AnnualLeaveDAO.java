package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveEntity;
import java.util.Optional;

public interface AnnualLeaveDAO {

  Optional<AnnualLeaveEntity> findById(Long annualLeaveId);

  void save(AnnualLeaveEntity leave);
}
