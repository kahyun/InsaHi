package com.playdata.HumanResourceManagement.employee.entity;

import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.publicEntity.FileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee {

    @Id
    @Column(name = "employee_id", unique = true, length = 36)
    private String employeeId;

    @Column(nullable = false)
    private String password;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String state;  // 상태 (Active, Inactive 등)
    private String positionName; // 직급명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = true)
    private DepartmentEntity department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = true)
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

    private LocalDate birthday; // 생일
    private LocalDate hireDate; // 입사일
    private LocalDate retireDate; // 퇴사일

    // 부서 ID 반환 (null일 경우 null 처리)
    public String getDepartmentId() {
        return department != null ? department.getDepartmentId() : null;
    }

    // 상태 반환, null일 경우 "상태 없음" 처리
    public String getStatus() {
        return state != null ? state : "상태 없음";
    }

    // 회사 코드 반환, null일 경우 null 처리
    public String getCompanyCode() {
        return company != null ? company.getCompanyCode() : null;
    }

    // 권한 목록에서 역할을 반환 (예시: 첫 번째 권한의 이름 반환)
    public String getRole() {
        return authorityList != null && !authorityList.isEmpty() ? authorityList.iterator().next().getAuthorityName() : "역할 없음";
    }
}
