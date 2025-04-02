package com.playdata.HumanResourceManagement.department.dto;

import com.playdata.HumanResourceManagement.employee.entity.Authority;
import com.playdata.HumanResourceManagement.employee.entity.Employee;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class UserDataDTO {
    private String employeeId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate birthday;
    private String departmentId;
    private String state;
    private LocalDate hireDate;
    private LocalDate retireDate;
    private String positionName;
    private String positionSalaryId;
    private int salaryStepId;
    private Set<Authority> authorityList;
    private String companyCode;

    // Constructor to initialize DTO fields
    public UserDataDTO(String employeeId, String name, String positionName, String email, String phoneNumber, String address, String gender, LocalDate birthday, String departmentId, String status, LocalDate hireDate, LocalDate retireDate) {
        this.employeeId = employeeId;
        this.name = name;
        this.positionName = positionName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.birthday = birthday;
        this.departmentId = departmentId;
        this.state = status;
        this.hireDate = hireDate;
        this.retireDate = retireDate;
    }

    // Default constructor
    public UserDataDTO() {}

    public UserDataDTO(String employeeId, String name, String positionName, String email, String phoneNumber, String address, String gender, LocalDate birthday) {
    }

    // Static method to create UserDataDTO from Employee entity
    public static UserDataDTO fromEntity(Employee employee) {
        return new UserDataDTO(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getPositionName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getAddress(),
                employee.getGender(),
                employee.getBirthday(), // LocalDate로 처리
                employee.getDepartmentId(),
                employee.getState(),
                employee.getHireDate(),
                employee.getRetireDate()
        );
    }

    // Getter and setter methods for all fields
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }

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

    public String getPositionSalaryId() { return positionSalaryId; }
    public void setPositionSalaryId(String positionSalaryId) { this.positionSalaryId = positionSalaryId; }

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
        private String address;
        private String gender;
        private LocalDate birthday;
        private String departmentId;
        private String state;
        private LocalDate hireDate;
        private LocalDate retireDate;
        private String positionSalaryId;
        private int salaryStepId;
        private Set<Authority> authorityList;
        private String companyCode;

        // Builder methods to set the fields
        public Builder employeeId(String employeeId) { this.employeeId = employeeId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder positionName(String positionName) { this.positionName = positionName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder phoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; return this; }
        public Builder address(String address) { this.address = address; return this; }
        public Builder gender(String gender) { this.gender = gender; return this; }
        public Builder birthday(LocalDate birthday) { this.birthday = birthday; return this; }
        public Builder departmentId(String departmentId) { this.departmentId = departmentId; return this; }
        public Builder state(String state) { this.state = state; return this; }
        public Builder hireDate(LocalDate hireDate) { this.hireDate = hireDate; return this; }
        public Builder retireDate(LocalDate retireDate) { this.retireDate = retireDate; return this; }
        public Builder positionSalaryId(String positionSalaryId) { this.positionSalaryId = positionSalaryId; return this; }
        public Builder salaryStepId(int salaryStepId) { this.salaryStepId = salaryStepId; return this; }
        public Builder authorityList(Set<Authority> authorityList) { this.authorityList = authorityList; return this; }
        public Builder companyCode(String companyCode) { this.companyCode = companyCode; return this; }

        // Build method to create UserDataDTO
        public UserDataDTO build() {
            return new UserDataDTO(employeeId, name, positionName, email, phoneNumber, address, gender, birthday, departmentId, state, hireDate, retireDate);
        }
    }

    // Static method to get a new Builder instance
    public static Builder builder() {
        return new Builder();
    }
}
