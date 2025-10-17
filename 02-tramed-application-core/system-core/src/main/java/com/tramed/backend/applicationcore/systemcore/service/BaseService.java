package com.tramed.backend.applicationcore.systemcore.service;

import com.tramed.backend.core.base.exception.UnauthorizedException;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.security.AuthenticatedUserProvider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class BaseService {

  private final AuthenticatedUserProvider authenticatedUserProvider;

  protected UserId requireCurrentUserId() {
    return authenticatedUserProvider
        .getCurrentUserId()
        .orElseThrow(() -> new UnauthorizedException("E0004"));
  }
}
