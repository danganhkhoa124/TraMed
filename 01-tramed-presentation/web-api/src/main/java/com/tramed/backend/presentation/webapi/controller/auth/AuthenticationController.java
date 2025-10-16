package com.tramed.backend.presentation.webapi.controller.auth;

import com.tramed.backend.core.base.exception.UnauthorizedException;
import com.tramed.backend.presentation.webapi.model.auth.LoginRequest;
import com.tramed.backend.presentation.webapi.model.auth.LoginResponse;
import com.tramed.backend.presentation.webapi.security.jwt.JwtProperties;
import com.tramed.backend.presentation.webapi.security.jwt.JwtTokenProvider;
import com.tramed.backend.presentation.webapi.security.user.AdminUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tra-med-api/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtProperties jwtProperties;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.username(), request.password()));
      AdminUserDetails userDetails = (AdminUserDetails) authentication.getPrincipal();
      String token = jwtTokenProvider.generateToken(userDetails);
      return ResponseEntity.ok(
          new LoginResponse(token, "Bearer", jwtProperties.expirationInMinutes()));
    } catch (AuthenticationException ex) {
      throw new UnauthorizedException("E0005");
    }
  }
}
