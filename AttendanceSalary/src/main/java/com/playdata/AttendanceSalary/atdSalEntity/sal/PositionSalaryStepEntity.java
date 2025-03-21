package com.playdata.AttendanceSalary.atdSalEntity.sal;

import com.playdata.common.publicEntity.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal; // 급여 관련 필드에서 사용
/**
 * 직급별 급여 단계 엔티티
 */
@Entity
@Table(name = "position_salary_step")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionSalaryStepEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_salary_id")
    private Long positionSalaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private PositionEntity position;

    @Column(name = "salary_step_id")
    private int salaryStepId;

    @Column(precision = 11, scale = 2)
    private BigDecimal baseSalary;

    @Column(precision = 11, scale = 2)
    private BigDecimal overtimeAllowance;

    private int baseAnnualLeave;

    private String companyCode;
}
