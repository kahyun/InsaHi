package com.playdata.attendanceSalary.atdSalDao.sal;
import com.playdata.attendanceSalary.atdSalEntity.sal.PositionEntity;
import java.util.Optional;


public interface PositionDao {
    PositionEntity savePosition(PositionEntity positionEntity);

    void deletePosition(PositionEntity positionEntity);

    Optional<PositionEntity> findById(Long positionId);

}
