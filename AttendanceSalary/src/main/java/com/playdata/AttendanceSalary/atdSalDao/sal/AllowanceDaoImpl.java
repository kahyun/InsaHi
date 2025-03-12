package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.AllowanceEntity;
import com.playdata.attendanceSalary.atdSalRepository.sal.AllowanceRepository;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AllowanceDaoImpl implements AllowanceDao {

  private AllowanceRepository allowanceRepository;

  @Override
  public AllowanceEntity saveAllowance(AllowanceEntity allowanceEntity) {
    allowanceRepository.save(allowanceEntity);
    return allowanceEntity;
  }


  @Override
  public Optional<AllowanceEntity> findAllowanceById(Long id) {
    return allowanceRepository.findById(id);
  }

  @Override
  public void deleteAllowanceById(Long id) {
    allowanceRepository.deleteById(id);
    allowanceRepository.flush();

  }

  //김재희
  @Override
  public List<AllowanceEntity> findByPayStubId(Long payStubId) {
    return List.of();
  }
}
