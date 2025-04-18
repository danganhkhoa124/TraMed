package com.tramed.backend.core.base.model.common;

import java.util.UUID;

/**
 * ID of user was logging
 *
 * @param value UUID of user id
 */
public record UserId(UUID value) {
  public UserId {
    if (value == null) {
      throw new NullPointerException("userId is null");
    }
  }
}
