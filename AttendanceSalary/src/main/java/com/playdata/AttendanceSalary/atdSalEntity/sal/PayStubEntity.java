package com.playdata.attendanceSalary.atdSalEntity.sal;

import com.playdata.attendanceSalary.atdSalDto.sal.PayStubResponseDTO;
import com.playdata.common.publicEntity.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/// 급여 명세
@Entity
@Table(name = "pay_stub")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayStubEntity extends DateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_stub_id")
    private Long payStubId;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Column(name = "base_salary", precision = 11, scale = 2)
    private BigDecimal baseSalary;

    @Column(name = "total_allowances", precision = 11, scale = 2)
    private BigDecimal totalAllowances;

    @Column(precision = 11, scale = 2)
    private BigDecimal overtimePay;

    @Column(precision = 11, scale = 2)
    private BigDecimal totalPayment;

    @Column(precision = 11, scale = 2)
    private BigDecimal totalDeductions;

    @Column(precision = 11, scale = 2)
    private BigDecimal netPay;
    private String companyCode;

    @Column(name = "employee_id")
    private String employeeId;

    public PayStubResponseDTO toPayStubResponseDTO() {
        PayStubResponseDTO payStubResponseDTO = new PayStubResponseDTO();
        payStubResponseDTO.setPayStubId(payStubId);
        payStubResponseDTO.setPaymentDate(paymentDate);
        payStubResponseDTO.setBaseSalary(baseSalary);
        payStubResponseDTO.setTotalAllowances(totalAllowances);
        payStubResponseDTO.setOvertimePay(overtimePay);
        payStubResponseDTO.setTotalPayment(totalPayment);
        payStubResponseDTO.setTotalDeductions(totalDeductions);
        payStubResponseDTO.setNetPay(netPay);
        payStubResponseDTO.setCompanyCode(companyCode);
        payStubResponseDTO.setEmployeeId(employeeId);
        return payStubResponseDTO;
    }


}