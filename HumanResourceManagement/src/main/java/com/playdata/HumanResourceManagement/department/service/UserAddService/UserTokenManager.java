package com.playdata.HumanResourceManagement.department.service.UserAddService;

import com.playdata.HumanResourceManagement.employee.authentication.EmpAuthenticationToken;
import com.playdata.HumanResourceManagement.employee.dto.MyUserDetail;
import com.playdata.HumanResourceManagement.employee.service.EmployeeUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserTokenManager implements InitializingBean {

  private String secret;
  private long tokenExpiryTime;
  private Key key;
  private final EmployeeUserDetailService userDetailService;

  public UserTokenManager(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.token-validity-in-second}") long tokenExpiryTime,
                          EmployeeUserDetailService userDetailService) {
    this.secret = secret;
    this.tokenExpiryTime = tokenExpiryTime;
    this.userDetailService = userDetailService;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    byte[] keyData = Decoders.BASE64.decode(secret);
    this.key = Keys.hmacShaKeyFor(keyData);
  }

  // JWT 토큰 생성
  public String createToken(Authentication authentication) {
    MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
    List<String> authorityList = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    long now = new Date().getTime();
    Date expiryDate = new Date(now + tokenExpiryTime * 1000);

    return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("companyCode", myUserDetail.getCompanyCode())
            .claim("auth", String.join(",", authorityList))
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  // JWT 토큰에서 인증 정보 추출
  public Authentication getAuthentication(String token) {
    Claims claims = parseClaims(token);
    List<GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    String employeeId = claims.getSubject();
    String companyCode = claims.get("companyCode", String.class);

    MyUserDetail userDetails = (MyUserDetail) userDetailService.loadUserByUsername(employeeId);
    return new EmpAuthenticationToken(userDetails, null, companyCode, authorities);
  }

  // JWT 유효성 검증
  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      System.out.println("Invalid JWT token: " + e.getMessage());
      return false;
    }
  }

  // JWT 파싱
  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
