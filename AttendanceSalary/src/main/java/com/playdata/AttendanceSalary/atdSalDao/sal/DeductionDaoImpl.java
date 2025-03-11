package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.DeductionEntity;
import com.playdata.attendanceSalary.atdSalRepository.sal.DeductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DeductionDaoImpl implements DeductionDao {
    private final DeductionRepository deductionRepository;

    @Override
    public void deleteById(Long deductionId) {
        deductionRepository.deleteById(deductionId);
    }

    @Override
    public DeductionEntity save(DeductionEntity deductionEntity) {
        return deductionRepository.save(deductionEntity);
    }

    @Override
    public Optional<DeductionEntity> fetchById(Long deductionId) {
        return deductionRepository.findById(deductionId);
    }
}
