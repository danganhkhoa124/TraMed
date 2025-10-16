package com.tramed.backend.presentation.webapi.security;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.security.AuthenticatedUserProvider;
import com.tramed.backend.presentation.webapi.security.user.ApplicationUserDetails;
import java.util.Optional;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class SecurityAuthenticatedUserProvider implements AuthenticatedUserProvider {

  @Override
  public Optional<UserId> getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }
    Object principal = authentication.getPrincipal();
    if (principal instanceof ApplicationUserDetails applicationUserDetails) {
      return Optional.of(applicationUserDetails.getUserId());
    }
    return Optional.empty();
  }
}
