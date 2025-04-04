package com.playdata.AttendanceSalary.atdSalRepository.sal;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

/**
 * Position(직급) 관련 Repository
 */
@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {
    List<PositionEntity> findAllByCompanyCode(@Param("companyCode") String companyCode);

    /**
     * 회사 코드로 직급 전체 조회
     *
     * @param companyCode 회사 코드
     * @return 직급 리스트
     */
    List<PositionEntity> findByCompanyCode(String companyCode);

    /**
     * 직급 ID로 단일 직급 조회
     *
     * @param id 직급 ID
     * @return 직급 엔티티 Optional
     */
    Optional<PositionEntity> findById(Long id);

}
