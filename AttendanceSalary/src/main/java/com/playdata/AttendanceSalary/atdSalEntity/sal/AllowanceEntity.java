package com.playdata.attendanceSalary.atdSalEntity.sal;

import com.playdata.attendanceSalary.atdSalDto.sal.AllowanceResponseDTO;
import com.playdata.common.publicEntity.DateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
//수당
@Entity
@Table(name = "allowance")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AllowanceEntity extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allowanceId;
    private String companyCode;
    private boolean taxExemption;
    private BigDecimal salary;
    private Long salaryId;
    private Long payStubId;


    public AllowanceResponseDTO toDTO(AllowanceResponseDTO responseDTO) {
        AllowanceResponseDTO dto = new AllowanceResponseDTO();
        dto.setAllowanceId(allowanceId);
        dto.setCompanyCode(companyCode);
        dto.setTaxExemption(taxExemption);
        dto.setSalary(salary);
        dto.setSalaryId(salaryId);
        dto.setPayStubId(payStubId);
        return dto;
    }
}
