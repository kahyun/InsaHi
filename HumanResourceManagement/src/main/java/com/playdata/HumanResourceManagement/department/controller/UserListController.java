package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/{companyCode}/employee")
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
    @GetMapping("/{employeeId}")
    public ResponseEntity<UserDataDTO> getEmployeeById(@PathVariable String companyCode, @PathVariable String employeeId) {
        UserDataDTO employee = employeeDataService.getEmployeeByCompanyCodeAndId(companyCode, employeeId);
        if (employee == null) {
            throw new ResponseStatusException(NOT_FOUND, "직원을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(employee);
    }

    /**
     * 직원 아이디에 맞게 직원 삭제하기
     *
     * @param companyCode 회사 코드
     * @param employeeId 삭제할 직원 ID
     * @return 삭제된 직원 정보
     */
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<UserDataDTO> deleteUser(@PathVariable String companyCode, @PathVariable String employeeId) {
        UserDataDTO deletedUser = employeeDataService.deleteUser(companyCode, employeeId);
        if (deletedUser == null) {
            throw new ResponseStatusException(NOT_FOUND, "삭제할 직원을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(deletedUser);
    }

    /**
     * 직원 아이디에 맞게 직원 정보 수정하기
     *
     * @param companyCode 회사 코드
     * @param employeeId 수정할 직원 ID
     * @param userDTO 수정할 직원 정보
     * @return 수정된 직원 정보
     */
    @PutMapping("/{employeeId}")
    public ResponseEntity<UserDataDTO> updateUser(
            @PathVariable String companyCode,
            @PathVariable String employeeId,
            @RequestBody UserDataDTO userDTO) {
        UserDataDTO updatedUser = employeeDataService.updateUser(companyCode, employeeId, userDTO);
        if (updatedUser == null) {
            throw new ResponseStatusException(NOT_FOUND, "수정할 직원을 찾을 수 없습니다.");
        }
        return ResponseEntity.ok(updatedUser);
    }
}