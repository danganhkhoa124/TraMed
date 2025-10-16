package com.tramed.backend.core.base.model.user;

import com.tramed.backend.core.base.model.common.UserId;

/**
 * Domain model representing an application user.
 *
 * @param userId unique identifier of the user
 * @param username username used for authentication
 * @param passwordHash password hash stored for the user
 * @param fullName display name of the user
 * @param active status flag indicating whether the user account is active
 * @param role the role granted to the user
 */
public record User(
    UserId userId,
    String username,
    String passwordHash,
    String fullName,
    boolean active,
    UserRole role) {}
