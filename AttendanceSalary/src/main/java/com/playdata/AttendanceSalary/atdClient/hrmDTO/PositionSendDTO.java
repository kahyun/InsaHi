package com.playdata.AttendanceSalary.atdClient.hrmDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionSendDTO {

    private String positionName;    // 직급명
    private Long positionSalaryId;  // 직급 호봉 아이디
    private int salaryStepId;       // 직급 호봉 단계 ID

}
