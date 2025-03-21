package com.playdata.HumanResourceManagement.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.playdata.AttendanceSalary.atdSalEntity.sal.PositionEntity;
import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.publicEntity.FileEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
@PersistenceUnit(unitName = "default")
public class Employee {

    @Id
    @Column(name = "employee_id", unique = true, length = 36)
    private String employeeId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(nullable = false)
    private String password;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // 직급 엔티티 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private PositionEntity position; // 직급(PositionEntity)와 연관

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "department_id", nullable = true)
    @JsonBackReference
    private DepartmentEntity department;

    private String teamId;
    private String state;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
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

    // 직급명 반환
    public String getPositionName() {
        return position != null ? position.getPositionName() : "직급명 없음";
    }

    // 직급 ID 반환
    public Long getPositionId() {
        return position != null ? position.getPositionId() : null; // 직급 ID를 반환
    }

    // 엔티티 저장 전 초기화 작업
    @PrePersist
    public void prePersist() {
        if (this.password == null || this.password.isEmpty()) {
            this.password = "1234"; // 기본 비밀번호 설정
        }
        if (this.authorityList == null) {
            this.authorityList = new HashSet<>();
        }
        if (this.employeeId == null) {
            this.employeeId = "2025" + UUID.randomUUID().toString().substring(0, 4); // employeeId 생성
        }
    }

    // 부서 정보 반환
    public String getDepartmentInfo() {
        return department != null ? department.getDepartmentName() : "Department is null";
    }

    // 부서 변경 메서드
    public void changeDepartment(DepartmentEntity newDepartment) {
        this.department = newDepartment;
    }

    // 직원 상태 반환
    public String getStatus() {
        return state != null ? state : "Status not available";
    }

    // 회사 코드 반환
    public String getCompanyCode() {
        return company != null ? company.getCompanyCode() : null;
    }

    // 직원 이름 반환
    public String getEmployeeName() {
        return name != null ? name : "이름 없음";
    }
}
