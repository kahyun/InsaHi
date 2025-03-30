package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{companyCode}")
@RequiredArgsConstructor
public class UserListController {

    private final EmployeeDataService employeeDataService;

    /**
     * 회사 코드와 직원 아이디에 맞게 직원 정보 가져오기
     *
     * @param companyCode 회사 코드
     * @param employeeId 직원 ID
     * @return 해당 직원의 정보
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<UserDataDTO> getEmployeeById(@PathVariable String companyCode, @PathVariable String employeeId) {
        try {
            // 회사 코드와 직원 ID를 전달하여 직원 정보를 조회
            UserDataDTO employee = employeeDataService.getEmployeeByCompanyCodeAndId(companyCode, employeeId);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 직원 아이디에 맞게 직원 삭제하기
     *
     * @param companyCode 회사 코드
     * @param employeeId 삭제할 직원 ID
     * @return 삭제된 직원 정보
     */
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<UserDataDTO> deleteUser(@PathVariable String companyCode, @PathVariable String employeeId) {
        try {
            UserDataDTO deletedUser = employeeDataService.deleteUser(companyCode, employeeId);
            return new ResponseEntity<>(deletedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 직원 아이디에 맞게 직원 정보 수정하기
     *
     * @param companyCode 회사 코드
     * @param employeeId 수정할 직원 ID
     * @param userDTO 수정할 직원 정보
     * @return 수정된 직원 정보
     */
    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<UserDataDTO> updateUser(@PathVariable String companyCode, @PathVariable String employeeId, @RequestBody UserDataDTO userDTO) {
        try {
            UserDataDTO updatedUser = employeeDataService.updateUser(companyCode, employeeId, userDTO);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 회사 코드 기반으로 직원 추가하기
     *
     * @param companyCode 회사 코드
     * @param userDTO 직원 정보
     * @return 추가된 직원 정보
     */
    @PostMapping("/employee")
    public ResponseEntity<UserDataDTO> createUserByCompanyCode(@PathVariable String companyCode, @RequestBody UserDataDTO userDTO) {
        try {
            userDTO.setCompanyCode(companyCode); // userDTO에 회사 코드 포함
            UserDataDTO createdUser = employeeDataService.createUser(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
