package com.playdata.HumanResourceManagement.userList.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/management")
public class UserLIstController {

    // 1. 사용자 리스트 조회
    @GetMapping("/list")
    public String list() {
        return "UserList";
    }
    // 2. 사용자 상세 정보 조회
    @GetMapping("/detail/{id}")
    public String detailUser() {
        return "UserList";
    }
}
