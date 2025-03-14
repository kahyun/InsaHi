package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.PositionSalaryStepEntity;
import com.playdata.attendanceSalary.atdSalRepository.sal.PositionSalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PositionSalaryDaoImpl implements PositionSalaryDao {

  private PositionSalaryRepository positionSalaryStepRepository;

  @Override
  public PositionSalaryStepEntity savePositionSalaryStep(
      PositionSalaryStepEntity positionSalaryStepEntity) {
    return positionSalaryStepRepository.save(positionSalaryStepEntity);
  }

  @Override
  public void deletePosition(PositionSalaryStepEntity positionSalaryStepEntity) {
    positionSalaryStepRepository.delete(positionSalaryStepEntity);
  }

  @Override
  public Optional<PositionSalaryStepEntity> findPositionSalaryStepById(Long positionSalaryID) {
    return positionSalaryStepRepository.findById(positionSalaryID);
  }


}

