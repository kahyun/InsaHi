package com.playdata.attendanceSalary.atdSalEntity.sal;
import com.playdata.Common.publicEntity.DateEntity;
import com.playdata.User.company.entity.Company;
import com.playdata.User.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "severance_pay")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeverancePayEntity  extends DateEntity {

    private String companyCode;

    @Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;



    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate retirementDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal severanceAmount;


}