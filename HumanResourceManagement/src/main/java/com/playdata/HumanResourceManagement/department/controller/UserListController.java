package com.playdata.HumanResourceManagement.department.controller;

import com.playdata.HumanResourceManagement.department.dto.UserDataDTO;
import com.playdata.HumanResourceManagement.department.service.EmployeeDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{companyCode}/employee")
@RequiredArgsConstructor
public class UserListController {

    private final EmployeeDataService employeeDataService;

    /**
     * 회사 코드에 따른 직원 목록 조회
     *
     * @param companyCode 회사 코드
     * @return 직원 리스트
     */
    @GetMapping("/list")
    public List<UserDataDTO> getAllEmployees(@PathVariable String companyCode) {
        // EmployeeDataService를 사용하여 직원 목록 조회
        return employeeDataService.getAllEmployees(companyCode);
    }

    /**
     * 직원 정보 추가
     *
     * @param userDTO 직원 정보
     * @return 추가된 직원 정보
     */
    @PostMapping("/addUser")
    public UserDataDTO addUser(@RequestBody UserDataDTO userDTO) {
        // 서비스 레이어를 통해 직원 추가
        return employeeDataService.addUser(userDTO);
    }

    /**
     * 직원 정보 삭제
     *
     * @param employeeId 삭제할 직원 ID
     * @return 삭제된 직원 정보
     */
    @DeleteMapping("/deleteUser/{employeeId}")
    public UserDataDTO deleteUser(@PathVariable String employeeId) {
        return employeeDataService.deleteUser(employeeId);
    }
}
