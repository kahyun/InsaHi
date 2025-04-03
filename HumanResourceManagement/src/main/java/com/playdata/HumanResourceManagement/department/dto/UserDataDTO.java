package com.playdata.HumanResourceManagement.department.dto;

import com.playdata.HumanResourceManagement.employee.entity.Authority;
import com.playdata.HumanResourceManagement.employee.entity.Employee;

import java.time.LocalDate;
import java.util.Set;

public class UserDataDTO {
    private String employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private String departmentId;
    private String state;
    private LocalDate hireDate;
    private LocalDate retireDate;
    private String positionName;
    private Long positionSalaryId;
    private int salaryStepId;
    private Set<Authority> authorityList;
    private String companyCode;

    // Constructor
    public UserDataDTO(String employeeId, String name, String positionName, String email, String phoneNumber,
                       String departmentId, String state, LocalDate hireDate, LocalDate retireDate, Long positionSalaryId) {
        this.employeeId = employeeId;
        this.name = name;
        this.positionName = positionName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.state = state;
        this.hireDate = hireDate;
        this.retireDate = retireDate;
        this.positionSalaryId = positionSalaryId;
    }

    // Default constructor
    public UserDataDTO() {}

    public UserDataDTO(String employeeId, String name, String email, String phoneNumber, String departmentId, String status, LocalDate hireDate, LocalDate retireDate) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.departmentId = departmentId;
        this.state = status;
        this.hireDate = hireDate;
        this.retireDate = retireDate;
    }

    // Static method to create UserDataDTO from Employee entity
    public static UserDataDTO fromEntity(Employee employee, String positionName) {
        return new UserDataDTO(
                employee.getEmployeeId(),
                employee.getName(),
                positionName,
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getDepartmentId(),
                employee.getState(),
                employee.getHireDate(),
                employee.getRetireDate(),
                employee.getPositionSalaryId()
        );
    }

    // 추가된 메서드: positionName 없이 Employee만 받을 수 있도록 오버로드
    public static UserDataDTO fromEntity(Employee employee) {
        return fromEntity(employee, "알 수 없음"); // 기본값 설정
    }

    // Getter and Setter
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public LocalDate getRetireDate() { return retireDate; }
    public void setRetireDate(LocalDate retireDate) { this.retireDate = retireDate; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public Long getPositionSalaryId() { return positionSalaryId; }
    public void setPositionSalaryId(Long positionSalaryId) { this.positionSalaryId = positionSalaryId; }

    public int getSalaryStepId() { return salaryStepId; }
    public void setSalaryStepId(int salaryStepId) { this.salaryStepId = salaryStepId; }

    public Set<Authority> getAuthorityList() { return authorityList; }
    public void setAuthorityList(Set<Authority> authorityList) { this.authorityList = authorityList; }

    public String getCompanyCode() { return companyCode; }
    public void setCompanyCode(String companyCode) { this.companyCode = companyCode; }

    // Builder pattern for constructing UserDataDTO
    public static class Builder {
        private String employeeId;
        private String name;
        private String positionName;
        private String email;
        private String phoneNumber;
        private String departmentId;
        private String state;
        private LocalDate hireDate;
        private LocalDate retireDate;
        private Long positionSalaryId;

        public Builder employeeId(String employeeId) { this.employeeId = employeeId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder positionName(String positionName) { this.positionName = positionName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder departmentId(String departmentId) { this.departmentId = departmentId; return this; }
        public Builder state(String state) { this.state = state; return this; }
        public Builder hireDate(LocalDate hireDate) { this.hireDate = hireDate; return this; }

        public UserDataDTO build() {
            return new UserDataDTO(employeeId, name, positionName, email, phoneNumber, departmentId, state, hireDate, retireDate, positionSalaryId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
