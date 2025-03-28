package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyCode}/employee")
@RequiredArgsConstructor
public class UserListController {

    private final EmployeeDataService employeeDataService;



    /**
     * 직원 정보 추가
     *
     * @param userDTO 직원 정보
     * @return 추가된 직원 정보
     */
    @PostMapping("/create")
    public ResponseEntity<UserDataDTO> createUser(@RequestBody UserDataDTO userDTO) {
        try {
            UserDataDTO createdUser = employeeDataService.create(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * 직원 정보 삭제
     *
     * @param employeeId 삭제할 직원 ID
     * @return 삭제된 직원 정보
     */
    @DeleteMapping("/delete/{employeeId}")
    public UserDataDTO deleteUser(@PathVariable String employeeId) {
        return employeeDataService.deleteUser(employeeId);
    }


}
