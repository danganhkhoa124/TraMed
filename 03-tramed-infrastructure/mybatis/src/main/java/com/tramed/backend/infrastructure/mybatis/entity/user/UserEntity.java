package com.tramed.backend.infrastructure.mybatis.entity.user;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.user.User;
import java.util.UUID;

/** Entity representing an application user in the persistence layer. */
public record UserEntity(
    UUID userId, String username, String passwordHash, String fullName, boolean active) {

  public User toDomain() {
    return new User(new UserId(userId), username, passwordHash, fullName, active);
  }
}
