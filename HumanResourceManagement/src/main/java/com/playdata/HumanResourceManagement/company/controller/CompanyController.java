package com.playdata.HumanResourceManagement.company.controller;

import com.playdata.HumanResourceManagement.company.dto.SignupRequestDTO;
import com.playdata.HumanResourceManagement.company.entity.Company;
import com.playdata.HumanResourceManagement.company.service.CompanyService;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
//    회사 정보 insert
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup(@RequestBody CompanyRequestDTO companyRequestDTO) {
//        companyService.insert(companyRequestDTO);
//        return ResponseEntity.ok(HttpStatus.OK);
//    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        Company savedCompany = companyService.insert(signupRequestDTO.getCompany());

        EmployeeRequestDTO employeeDTO = signupRequestDTO.getEmployee();
        employeeDTO.setCompanyCode(savedCompany.getCompanyCode());


        Employee employee = employeeService.insertEmployee(employeeDTO);

        employeeService.addAdminAndUserRoles(employee);

        return ResponseEntity.ok(HttpStatus.OK);
    }
}
