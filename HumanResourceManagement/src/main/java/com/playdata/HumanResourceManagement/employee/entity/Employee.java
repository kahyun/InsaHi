package com.playdata.HumanResourceManagement.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.entity.DepartmentEntity;
import com.playdata.HumanResourceManagement.publicEntity.FileEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Table(name = "employee")
@ToString(onlyExplicitlyIncluded = true)
public class Employee {

    @Id
    @Column(name = "employee_id", unique = true, length = 36)
    private String employeeId;

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String state; // 상태
    private String positionName;
    private String positionSalaryId;
    private Integer salaryStepId;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(nullable = true)
    private String password;


  private LocalDate hireDate;
  private LocalDate retireDate;


    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "department_id", nullable = true)
    @JsonBackReference
    private DepartmentEntity department;


    @Column(name = "birthday", nullable = true) // nullable 속성 추가
    private LocalDate birthday; // Optional 대신 LocalDate로 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", nullable = true)
    @JsonBackReference
    private Company company;

  @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//  @JoinColumn(name = "profile_image", referencedColumnName = "profile_image", nullable = true)
  private FileEntity profileImage;  // 프로필 이미지

    @ManyToMany
    @JoinTable(
            name = "employee_authority",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")}
    )
    private Set<Authority> authorityList;

    // 기본 생성자
    public Employee() {}

    // 모든 인자를 받는 생성자
    public Employee(String employeeId, String name, String email, String phoneNumber, String address,
                    String gender, String state, String positionName, String positionSalaryId,
                    int salaryStepId, LocalTime startTime, String password, LocalDate hireDate,
                    DepartmentEntity department, LocalDate retireDate, LocalDate birthday,
                    Company company, Set<Authority> authorityList) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.state = state;
        this.positionName = positionName;
        this.positionSalaryId = positionSalaryId;
        this.salaryStepId = salaryStepId;
        this.startTime = startTime;
        this.password = password;
        this.hireDate = hireDate;
        this.department = department;
        this.retireDate = retireDate;
        this.birthday = birthday;
        this.company = company;
        this.authorityList = authorityList;
    }

    @PrePersist
    public void prePersist() {
        if (this.employeeId == null) {
            this.employeeId = UUID.randomUUID().toString(); // UUID로 기본값 설정
        }
        if (this.password == null) {
            this.password = "defaultPassword"; // 기본 비밀번호 설정
        }
        if (this.birthday == null) {
            this.birthday = LocalDate.now(); // 기본 생일 설정
        }
        if (this.authorityList == null) {
            this.authorityList = new HashSet<>(); // 권한 리스트 초기화
        }
    }

    public String getDepartmentId() {
        return (this.department != null) ? this.department.getDepartmentId() : "Unknown"; // 부서 ID 가져오기
    }

    public String getCompanyCode() {
        return (this.company != null) ? this.company.getCompanyCode() : "Unknown"; // 회사 코드 가져오기
    }

    public String getDepartmentInfo() {
        return (this.department != null) ? this.department.getDepartmentName() : "No department assigned"; // 부서명 가져오기
    }

    public void changeDepartment(DepartmentEntity department) {
        this.department = department; // 부서 변경
    }

    public Set<String> getAuthorityNames() {
        return authorityList.stream()
                .map(Authority::getAuthorityName) // 권한 이름 가져오기
                .collect(Collectors.toSet());
    }

    // 상태 반환
    public String getStatus() {
        return this.state; // 상태 반환
    }

    // DTO 변환 메서드
    public UserDataDTO toUserDataDTO() {
        return new UserDataDTO(employeeId, name, positionName, email, phoneNumber, address, gender,
                birthday, getDepartmentId(), state, hireDate, retireDate); // birthday를 Optional 없이 전달
    }

    public void setDepartmentId(String departmentId) {
    }

    public void setPosition(String positionName) {
    }
}
