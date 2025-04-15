package com.tramed.backend.core.base.model.notification;

import java.util.UUID;

/**
 * ID of notification
 *
 * @param notificationId UUID of notification id
 */
public record NotificationId(UUID notificationId) {
  public NotificationId {
    if (notificationId == null) {
      throw new NullPointerException("notificationId is null");
    }
  }
}
