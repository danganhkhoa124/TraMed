package com.tramed.backend.core.base.model.notification;

import java.util.UUID;

/**
 * ID of notification
 *
 * @param value UUID of notification id
 */
public record NotificationId(UUID value) {
  public NotificationId {
    if (value == null) {
      throw new NullPointerException("notificationId is null");
    }
  }
}
