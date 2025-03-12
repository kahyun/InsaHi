package com.playdata.attendanceSalary.atdSalRepository.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.DeductionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeductionRepository extends JpaRepository<DeductionEntity,Long> {
    List<DeductionEntity> findByPayStubId(Long payStubId);

}
