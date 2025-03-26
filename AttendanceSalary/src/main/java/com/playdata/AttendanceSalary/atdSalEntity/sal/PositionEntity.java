package com.playdata.AttendanceSalary.atdSalEntity.sal;

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
    private String companyCode;  // 회사 코드
    private String employeeId;  // 직원 코드

}
