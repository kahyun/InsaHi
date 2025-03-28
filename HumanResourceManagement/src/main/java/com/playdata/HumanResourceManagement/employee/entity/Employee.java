package com.playdata.HumanResourceManagement.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.publicEntity.FileEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id", unique = true, length = 36)
    private String employeeId;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String state;
    private String positionName;
    private Long positionSalaryId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(nullable = true)
    private String password;

    @Column(name = "hire_date")
    private LocalDate hireDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "department_id", nullable = true)  // 외래키 컬럼만 지정
  @JsonBackReference  // 순환 참조 방지
  private DepartmentEntity department; // 부서
  private LocalDate retireDate;

    @Column(name = "birthday")
    private LocalDate birthday;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = true)
    @JsonBackReference
    private Company company;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private FileEntity profileImage;

    @ManyToMany
    @JoinTable(
            name = "employee_authority",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")}
    )
    private Set<Authority> authorityList;

    // 권한 이름만 반환
    public Set<String> getAuthorityNames() {
        return authorityList.stream()
                .map(Authority::getAuthorityName)
                .collect(Collectors.toSet());
    }

    // 부서 ID 반환
    public String getDepartmentId() {
        return department != null ? department.getDepartmentId() : null;
    }

    // 상태 반환
    public String getStatus() {
        return state != null ? state : "상태 없음";
    }

    // 회사 코드 반환
    public String getCompanyCode() {
        return company != null ? company.getCompanyCode() : null;
    }

    // 엔티티 저장 전에 기본값 설정
    @PrePersist
    public void prePersist() {
        if (this.password == null) {
            this.password = "1234";
        }
        if (this.authorityList == null) {
            this.authorityList = new HashSet<>();
        }
        if (this.employeeId == null) {
            this.employeeId = "2025" + UUID.randomUUID().toString().substring(0, 4);
        }
    }


  // 부서 이름 반환 (부서가 없는 경우 "Department is null" 반환)
  public String getDepartmentInfo() {
    return department != null ? department.getDepartmentName() : "Department is null";
  }

    // 부서 변경
    public void changeDepartment(DepartmentEntity newDepartment) {
        this.department = newDepartment;
    }

    // 역할 반환
    public String getRole() {
        return authorityList != null && !authorityList.isEmpty()
                ? authorityList.iterator().next().getAuthorityName()
                : "역할 없음";
    }
}
