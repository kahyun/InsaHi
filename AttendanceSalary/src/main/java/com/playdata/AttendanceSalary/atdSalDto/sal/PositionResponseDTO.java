package com.playdata.AttendanceSalary.atdSalDto.sal;

import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor  // 생성자 자동 생성
public class PositionResponseDTO {
    private Long positionId;
    private String positionName;
    private String companyCode;

    public PositionResponseDTO() {}

    // PositionResponseDTO -> PositionEntity 변환
    public PositionEntity toEntity() {
        // 생성자를 사용하여 PositionEntity 객체 생성
        return new PositionEntity(this.positionId, this.positionName, this.companyCode);
    }
}
