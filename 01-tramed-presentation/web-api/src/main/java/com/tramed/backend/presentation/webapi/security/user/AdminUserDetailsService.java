package com.tramed.backend.presentation.webapi.security.user;

import com.tramed.backend.applicationcore.systemcore.service.user.UserService;
import com.tramed.backend.core.base.exception.UnauthorizedException;
import com.tramed.backend.core.base.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userService
        .findByUsername(username)
        .filter(User::active)
        .map(AdminUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public AdminUserDetails loadActiveUser(String username) {
    return userService
        .findByUsername(username)
        .filter(User::active)
        .map(AdminUserDetails::new)
        .orElseThrow(() -> new UnauthorizedException("E0005"));
  }
}
