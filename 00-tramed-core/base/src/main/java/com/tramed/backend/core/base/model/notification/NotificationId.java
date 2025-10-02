package com.tramed.backend.core.base.model.notification;

import com.tramed.backend.core.base.exception.InvalidArgumentModelException;
import java.util.UUID;

/**
 * ID of notification
 *
 * @param value UUID of notification id
 */
public record NotificationId(UUID value) {
  public NotificationId {
    if (value == null) {
      throw new InvalidArgumentModelException("notificationId is null");
    }
  }
}
