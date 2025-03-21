package com.playdata.AttendanceSalary.atdSalRepository.sal;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    /**
     * 직급 ID로 직급 조회
     */
    List<PositionEntity> findByPositionId(Long positionId);

    /**
     * 회사 코드로 직급 전체 조회
     */
    List<PositionEntity> findByCompanyCode(String companyCode);

    /**
     * 직급 ID로 단일 직급 조회
     */
    Optional<PositionEntity> findById(Long id);

    /**
     * 회사 코드와 직급 ID 기준으로 단일 직급 조회
     */
    Optional<PositionEntity> findByCompanyCodeAndPositionId(String companyCode, Long positionId);
}
