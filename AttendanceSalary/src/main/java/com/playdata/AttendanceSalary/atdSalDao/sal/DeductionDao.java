package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.DeductionEntity;
import java.util.Optional;

public interface DeductionDao{
    void deleteById(Long deductionId);
    DeductionEntity save(DeductionEntity deductionEntity);
    Optional<DeductionEntity> fetchById(Long deductionId);


}
