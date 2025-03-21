package com.playdata.HumanResourceManagement.department.feign.dto;

import com.playdata.HumanResourceManagement.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DownloadPositionDTO {
    private String companyCode;  // 회사 코드
    private List<PositionWithEmployeesDTO> positions;  // 직급별 직원 리스트

    // PositionWithEmployeesDTO 객체에서 positionId를 가져오는 메서드
    public Long getPositionId() {
        // 여기서는 positions 리스트에서 첫 번째 PositionWithEmployeesDTO 객체의 positionId를 반환한다고 가정
        return this.positions.isEmpty() ? null : this.positions.get(0).getPositionId();
    }

    // PositionWithEmployeesDTO 객체에서 positionName을 가져오는 메서드
    public String getPositionName() {
        // 여기서는 positions 리스트에서 첫 번째 PositionWithEmployeesDTO 객체의 positionName을 반환한다고 가정
        return this.positions.isEmpty() ? null : this.positions.get(0).getPositionName();
    }

    // PositionWithEmployeesDTO 객체에서 salaryStep을 가져오는 메서드
    public Integer getSalaryStep() {
        // 여기서는 positions 리스트에서 첫 번째 PositionWithEmployeesDTO 객체의 salaryStep을 반환한다고 가정
        return this.positions.isEmpty() ? null : this.positions.get(0).getSalaryStep();
    }

    // PositionWithEmployeesDTO 클래스
    @Data
    @Builder
    public static class PositionWithEmployeesDTO {
        private String positionName;  // 직급 이름
        private List<EmployeeDTO> employees;  // 직원 목록
        private Long positionId;  // 직급 ID
        private Integer salaryStep; // 급여 단계

        // 직급 ID 반환
        public Long getPositionId() {
            return this.positionId;
        }

        // 급여 단계 반환
        public Integer getSalaryStep() {
            return this.salaryStep;
        }
    }

    @Data
    @Builder
    public static class EmployeeDTO {
        private String employeeId;  // 직원 ID
        private String name;        // 직원 이름

        // Employee 엔티티를 EmployeeDTO로 변환하는 메서드
        public static EmployeeDTO fromEmployee(Employee employee) {
            return new EmployeeDTO(
                    employee.getEmployeeId(),
                    employee.getEmployeeName()
            );
        }
    }
}
