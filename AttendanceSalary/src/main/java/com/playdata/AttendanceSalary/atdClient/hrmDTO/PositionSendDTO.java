package com.playdata.AttendanceSalary.atdClient.hrmDTO;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class PositionSendDTO {

    private Long positionId;    // 직급 ID
    private String positionName; // 직급 이름
    private String companyCode;  // 회사 코드
    private String employeeId;   // 직원 코드

}
