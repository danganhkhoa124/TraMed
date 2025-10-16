package com.tramed.backend.core.base.security;

import com.tramed.backend.core.base.model.common.UserId;
import java.util.Optional;

/**
 * Provides access to information about the authenticated user in the current execution context.
 */
public interface AuthenticatedUserProvider {
  /**
   * Returns the identifier of the authenticated user if available.
   *
   * @return an {@link Optional} containing the {@link UserId} when the current request has an
   *     authenticated user, or an empty {@link Optional} otherwise.
   */
  Optional<UserId> getCurrentUserId();
}
