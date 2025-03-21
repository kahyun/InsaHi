package com.playdata.AttendanceSalary.setConfig;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(Long positionId) {
        super("직급을 찾지 못했습니다 " + positionId);
    }
}
