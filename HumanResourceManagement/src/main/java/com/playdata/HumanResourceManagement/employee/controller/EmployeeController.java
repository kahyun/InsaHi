package com.playdata.HumanResourceManagement.employee.controller;

import com.playdata.HumanResourceManagement.employee.authentication.TokenManager;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final TokenManager tokenManager;


    @GetMapping("/{employeeId}/company/start-time")
    public ResponseEntity<LocalTime> getCompanyStartTime(@PathVariable("employeeId") String employeeId) {
        LocalTime startTime = employeeService.findCompanyStartTimeByEmployeeId(employeeId);
        log.info("controller 단 : getCompanyStartTime: {}", startTime);
        return ResponseEntity.ok(startTime);
    }


    @GetMapping("/find")
    /// 김다울
    public EmployeeResponseDTO findEmployee(@RequestParam("employeeId") String employeeId) {
        EmployeeResponseDTO employeeResponseDTO = employeeService.findEmployeeById(employeeId);
        return employeeResponseDTO;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        System.out.println("loginDTO: " + loginDTO);
        //1. 사용자정보를 담은 인증객체생성
        //2. 인증처리
        Authentication authentication = employeeService.signin(loginDTO);
        //3. 인증이 완료되면 인증객체를 이용해서 토큰생성하기
        String jwt = tokenManager.createToken(authentication);

        System.out.println("jwt: " + jwt);

        //4. 응답헤더에 토큰을 내보내기
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);

        System.out.println("성공 !!!!!!!~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!!");
        return new ResponseEntity<>(jwt, headers, HttpStatus.OK);
    }


    // gateway로 하면 지금만들어놓은거에서 좀 바뀌나?

}
