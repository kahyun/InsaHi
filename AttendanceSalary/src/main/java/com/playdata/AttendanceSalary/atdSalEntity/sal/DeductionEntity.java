package com.playdata.attendanceSalary.atdSalEntity.sal;


import com.playdata.attendanceSalary.atdSalDto.sal.DeductionResponseDTO;
import com.playdata.common.publicEntity.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/// 공제 항목
@Entity
@Table(name = "deduction")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DeductionEntity extends DateEntity {
    //공제
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deduction_id")
    private Long deductionId;

    @Column(name = "payroll_details_id", nullable = false)
    private Long payrollDetailsId;

    @Column(name = "payrollItem_id", nullable = false)
    private Long payrollItemId;

    @Column(name = "amount", precision = 11, scale = 2)
    private BigDecimal amount;

    private Long salaryId;
    private Long payStubId;

    public DeductionResponseDTO toResponseDTO() {
        DeductionResponseDTO responseDTO = new DeductionResponseDTO();
        responseDTO.setPayrollDetailsId(this.payrollDetailsId);
        responseDTO.setPayrollItemId(this.payrollItemId);
        responseDTO.setAmount(this.amount);
        responseDTO.setSalaryId(this.salaryId);
        responseDTO.setPayStubId(this.payStubId);
        return responseDTO;

    }

}