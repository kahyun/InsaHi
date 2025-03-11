package com.playdata.attendanceSalary.atdSalDao.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.PayStubEntity;
import com.playdata.attendanceSalary.atdSalRepository.sal.PayStubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PayStubDaoImpl implements PayStubDao {
    private final PayStubRepository payStubRepository;

    @Override
    public PayStubEntity save(PayStubEntity payStubEntity) {
        return payStubRepository.save(payStubEntity);
    }

    @Override
    public void update(PayStubEntity payStubEntity) {
        payStubRepository.save(payStubEntity);
    }

    @Override
    public void delete(PayStubEntity payStubEntity) {
        payStubRepository.delete(payStubEntity);
    }
}
