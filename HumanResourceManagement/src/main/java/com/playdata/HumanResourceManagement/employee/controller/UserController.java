package com.playdata.HumanResourceManagement.employee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관리 컨트롤러
 */
@RestController
@RequestMapping("/api/{companyCode}/user")
public class UserController {

    /**
     * 회사 코드 기반 사용자 리스트 조회
     * GET /api/{companyCode}/user/list
     */
    @GetMapping("/list")
    public ResponseEntity<?> getUserList(@PathVariable("companyCode") String companyCode) {
        // TODO: 사용자 리스트 조회 로직
        return ResponseEntity.ok("사용자 리스트 조회: " + companyCode);
    }

    /**
     * 회사 코드 기반 단일 사용자 조회
     * GET /api/{companyCode}/user/about/{employeeId}
     */
    @GetMapping("/about/{employeeId}")
    public ResponseEntity<?> getUserInfo(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("employeeId") String employeeId
    ) {
        // TODO: 사용자 단일 조회 로직
        return ResponseEntity.ok("사용자 조회: " + employeeId + " (회사 코드: " + companyCode + ")");
    }

    /**
     * 회사 코드 기반 단일 사용자 수정
     * PUT /api/{companyCode}/user/edit/{employeeId}
     */
    @PutMapping("/edit/{employeeId}")
    public ResponseEntity<?> updateUser(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("employeeId") String employeeId
            // @RequestBody UserUpdateRequestDTO userUpdateRequestDTO (DTO 추가하면 좋아요!)
    ) {
        // TODO: 사용자 수정 로직
        return ResponseEntity.ok("사용자 수정 완료: " + employeeId);
    }

    /**
     * 회사 코드 기반 단일 사용자 추가
     * POST /api/{companyCode}/user/create
     */
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @PathVariable("companyCode") String companyCode
            // @RequestBody UserCreateRequestDTO userCreateRequestDTO (DTO 추가 추천!)
    ) {
        // TODO: 사용자 생성 로직
        return ResponseEntity.ok("사용자 생성 완료 (회사 코드: " + companyCode + ")");
    }

    /**
     * 회사 코드 기반 단일 사용자 삭제
     * DELETE /api/{companyCode}/user/delete/{employeeId}
     */
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteUser(
            @PathVariable("companyCode") String companyCode,
            @PathVariable("employeeId") String employeeId
    ) {
        // TODO: 사용자 삭제 로직
        return ResponseEntity.ok("사용자 삭제 완료: " + employeeId);
    }
}
