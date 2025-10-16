package com.tramed.backend.presentation.webapi.security.user;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.user.User;
import com.tramed.backend.core.base.model.user.UserRole;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUserDetails implements UserDetails {

  private final User user;

  public ApplicationUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.role().name()));
  }

  @Override
  public String getPassword() {
    return user.passwordHash();
  }

  @Override
  public String getUsername() {
    return user.username();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return user.active();
  }

  public UserId getUserId() {
    return user.userId();
  }

  public String getFullName() {
    return user.fullName();
  }

  public UserRole getRole() {
    return user.role();
  }
}
