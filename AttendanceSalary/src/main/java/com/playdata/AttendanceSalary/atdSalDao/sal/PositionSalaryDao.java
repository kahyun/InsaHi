package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.PositionSalaryStepEntity;
import java.util.Optional;

public interface PositionSalaryDao {

  PositionSalaryStepEntity savePositionSalaryStep(
      PositionSalaryStepEntity positionSalaryStepEntity);

  void deletePosition(PositionSalaryStepEntity positionSalaryStepEntity);

  Optional<PositionSalaryStepEntity> findPositionSalaryStepById(Long positionSalaryID);

}
