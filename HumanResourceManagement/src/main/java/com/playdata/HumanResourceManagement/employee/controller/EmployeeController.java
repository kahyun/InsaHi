package com.playdata.HumanResourceManagement.employee.controller;

import com.playdata.HumanResourceManagement.employee.authentication.TokenManager;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeRequestDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeResponseDTO;
import com.playdata.HumanResourceManagement.employee.dto.EmployeeUpdateDTO;
import com.playdata.HumanResourceManagement.employee.dto.LoginDTO;
import com.playdata.HumanResourceManagement.employee.dto.MyUserDetail;
import com.playdata.HumanResourceManagement.employee.dto.ProfileCardDTO;
import com.playdata.HumanResourceManagement.employee.dto.UpdatePasswordDTO;
import com.playdata.HumanResourceManagement.employee.entity.Employee;
import com.playdata.HumanResourceManagement.employee.service.EmployeeService;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

  private final EmployeeService employeeService;
  private final TokenManager tokenManager;

  //login
  @PostMapping("/login")
  public ResponseEntity<Map> login(@RequestBody LoginDTO loginDTO) {
    System.out.println("loginDTO: " + loginDTO);
    //1. 사용자정보를 담은 인증객체생성
    //2. 인증처리
    Authentication authentication = employeeService.login(loginDTO);
    //3. 인증이 완료되면 인증객체를 이용해서 토큰생성하기
    String jwt = tokenManager.createToken(authentication);
    MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();

    System.out.println("jwt: " + jwt + ",길이" + myUserDetail.getUsername().length());

    //4. 응답헤더에 토큰을 내보내기
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + jwt);

    System.out.println("성공 !!!!!!!~~~~~~~~~~~~~~~~~!!!!!!!!!!!!!!");
    return new ResponseEntity<>(Map.of(
        "jwt", jwt,
        "employeeId", myUserDetail.getUsername(),
        "companyCode", myUserDetail.getCompanyCode(),
        "auth", myUserDetail.getAuthorities().stream()
            //각 GrantedAuthority 객체에서 문자열 권한 값을 추출
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))), headers, HttpStatus.OK);

  }


  @GetMapping("/{employeeId}/company/start-time")
  public ResponseEntity<LocalTime> getCompanyStartTime(
      @PathVariable("employeeId") String employeeId) {
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


  @GetMapping("/getallemployeeids")
  public List<String> getAllEmployeeIds() {
    return employeeService.getAllEmployeeIds();
  }


  //mypage 왼쪽 작은 프로필
  @GetMapping("/{employeeId}/profilecard")
  public ResponseEntity<ProfileCardDTO> getProfileCard(
      @PathVariable("employeeId") String employeeId) {
    ProfileCardDTO response = employeeService.getProfileCard(employeeId);
    System.out.println("result result result : " + response);
    return ResponseEntity.ok(response);

  }

  //개인정보 수정페이지 출력
  @GetMapping("/{employeeId}/employeeinfo")
  public ResponseEntity<EmployeeResponseDTO> getEmployeeInfo(
      @PathVariable("employeeId") String employeeId) {
    EmployeeResponseDTO response = employeeService.getEmployeeInfo(employeeId);

    return ResponseEntity.ok(response);
  }

  //개인정보 수정페이지 업데이트
  @PutMapping("/{employeeId}/update")
  public ResponseEntity<EmployeeResponseDTO> updateEmployeeInfo(
      @PathVariable("employeeId") String employeeId,
      @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {

    EmployeeResponseDTO response = employeeService.updateEmployeeInfo(employeeId,
        employeeUpdateDTO);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{employeeId}/updatepassword")
  public ResponseEntity<EmployeeResponseDTO> updateEmployeePassword(
      @PathVariable("employeeId") String employeeId,
      @RequestBody UpdatePasswordDTO updatePasswordDTO) {
    EmployeeResponseDTO response =
        employeeService.updatePassword(
            employeeId, updatePasswordDTO);
    System.out.println("currentPassword: " + updatePasswordDTO.getCurrentPassword());
    System.out.println("newPassword: " + updatePasswordDTO.getNewPassword());

    return ResponseEntity.ok(response);
  }

  //회원등록
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/insertemployee")
  public ResponseEntity<?> insertEmployee(
      @RequestBody EmployeeRequestDTO employeeRequestDTO) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    if (!(principal instanceof MyUserDetail myUserDetail)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "로그인이 필요합니다."));
    }

    List<String> authorities = myUserDetail.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    if (!(authorities.contains("ROLE_ADMIN") && authorities.contains("ROLE_USER"))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "등록 권한이 없습니다."));
    }

    employeeRequestDTO.setCompanyCode(myUserDetail.getCompanyCode());

    Employee employee = employeeService.employeeInsert(employeeRequestDTO);
    employeeService.addUserRoles(employee);

    return ResponseEntity.ok(Map.of("message", "직원등록 성공!"));
  }

  //    채팅방 초대 멤버 조회
  @GetMapping("/all")
  public List<Employee> getMemberList() {
    return employeeService.getMemberList();
  }
}