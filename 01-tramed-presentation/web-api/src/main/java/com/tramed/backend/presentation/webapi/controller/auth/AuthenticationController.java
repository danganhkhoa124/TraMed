package com.tramed.backend.presentation.webapi.controller.auth;

import com.tramed.backend.applicationcore.systemcore.service.user.RegisterUserCommand;
import com.tramed.backend.applicationcore.systemcore.service.user.UserService;
import com.tramed.backend.core.base.exception.UnauthorizedException;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.presentation.webapi.model.auth.LoginRequest;
import com.tramed.backend.presentation.webapi.model.auth.LoginResponse;
import com.tramed.backend.presentation.webapi.model.auth.RegisterRequest;
import com.tramed.backend.presentation.webapi.model.auth.RegisterResponse;
import com.tramed.backend.presentation.webapi.model.auth.UpdateUserStatusRequest;
import com.tramed.backend.presentation.webapi.security.jwt.JwtProperties;
import com.tramed.backend.presentation.webapi.security.jwt.JwtTokenProvider;
import com.tramed.backend.presentation.webapi.security.jwt.JwtTokenStore;
import com.tramed.backend.presentation.webapi.security.user.ApplicationUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tra-med-api/auth")
public class AuthenticationController {

  private static final String BEARER_PREFIX = "Bearer ";

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final JwtProperties jwtProperties;
  private final UserService userService;
  private final JwtTokenStore jwtTokenStore;

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(request.username(), request.password()));
      ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
      String token = jwtTokenProvider.generateToken(userDetails);
      jwtTokenStore.store(token, Duration.ofMinutes(jwtProperties.expirationInMinutes()));
      return ResponseEntity.ok(
          new LoginResponse(
              token,
              "Bearer",
              jwtProperties.expirationInMinutes(),
              userDetails.getRole().name(),
              userDetails.getFullName()));
    } catch (AuthenticationException ex) {
      throw new UnauthorizedException("E0005");
    }
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    extractToken(request).ifPresent(jwtTokenStore::remove);
    SecurityContextHolder.clearContext();
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
    var registeredUser =
        userService.registerUser(
            new RegisterUserCommand(request.username(), request.password(), request.fullName()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            new RegisterResponse(
                registeredUser.userId().value(),
                registeredUser.username(),
                registeredUser.active()));
  }

  @PatchMapping("/users/{userId}/status")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> updateUserStatus(
      @PathVariable UUID userId, @RequestBody @Valid UpdateUserStatusRequest request) {
    userService.updateUserStatus(new UserId(userId), request.active());
    return ResponseEntity.noContent().build();
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith(BEARER_PREFIX)) {
      return Optional.of(header.substring(BEARER_PREFIX.length()));
    }
    return Optional.empty();
  }
}
