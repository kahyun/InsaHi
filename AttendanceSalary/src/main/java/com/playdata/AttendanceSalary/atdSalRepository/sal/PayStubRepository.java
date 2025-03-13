package com.playdata.attendanceSalary.atdSalRepository.sal;

import com.playdata.attendanceSalary.atdSalEntity.sal.PayStubEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayStubRepository extends JpaRepository<PayStubEntity,Long> {
    PayStubEntity findByEmployeeId(String employeeId);
    /// sort 추ㄷ
}
