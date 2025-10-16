package com.tramed.backend.presentation.webapi.security;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.security.AuthenticatedUserProvider;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestSecurityAuthenticatedUserProvider implements AuthenticatedUserProvider {

  private final UserId testUserId;

  public TestSecurityAuthenticatedUserProvider(
      @Value("${security.test-user-id:22222222-2222-2222-2222-222222222222}") UUID testUserId) {
    this.testUserId = new UserId(testUserId);
  }

  @Override
  public Optional<UserId> getCurrentUserId() {
    return Optional.of(testUserId);
  }
}
