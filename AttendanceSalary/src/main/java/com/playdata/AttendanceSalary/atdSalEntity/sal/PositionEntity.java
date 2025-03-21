package com.playdata.AttendanceSalary.atdSalEntity.sal;

import com.playdata.AttendanceSalary.atdSalDto.sal.PositionResponseDTO;
import com.playdata.common.publicEntity.DateEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 직급(Position) 엔티티
 */
@Entity
@Table(name = "position")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;

    @Column(nullable = false, length = 100)
    private String positionName;

    @Column(nullable = false, length = 50)
    private String companyCode;

    /**
     * 급여 단계 매핑 (양방향 연관관계)
     */
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PositionSalaryStepEntity> salarySteps;

    /**
     * DTO 변환 메서드
     */
    public PositionResponseDTO toDTO() {
        return new PositionResponseDTO(this.positionId, this.positionName, this.companyCode);
    }

    // PositionEntity 클래스에 추가
    public PositionEntity(Long positionId, String positionName, String companyCode) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.companyCode = companyCode;
    }




}
