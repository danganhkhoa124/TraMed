package com.tramed.backend.core.base.model.common;

import java.util.UUID;

/**
 * ID of user was logging
 *
 * @param userId UUID of user id
 */
public record UserId(UUID userId) {
  public UserId {
    if (userId == null) {
      throw new NullPointerException("userId is null");
    }
  }
}
