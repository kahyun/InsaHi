package com.playdata.attendanceSalary.atdSalDao.atd;

import com.playdata.attendanceSalary.atdSalEntity.atd.AnnualLeaveEntity;
import com.playdata.attendanceSalary.atdSalRepository.atd.AnnualLeaveRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AnnualLeaveDAOImpl implements AnnualLeaveDAO {

  private final AnnualLeaveRepository annualLeaveRepository;

  @Override
  public Optional<AnnualLeaveEntity> findById(Long annualLeaveId) {
    return annualLeaveRepository.findById(annualLeaveId);
  }

  @Override
  public void save(AnnualLeaveEntity leave) {
    annualLeaveRepository.save(leave);
  }
}
