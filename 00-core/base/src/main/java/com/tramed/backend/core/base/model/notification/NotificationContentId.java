package com.tramed.backend.core.base.model.notification;

import java.util.UUID;

/**
 * ID of notification content
 *
 * @param value UUID of notification content
 */
public record NotificationContentId(UUID value) {
  public NotificationContentId {
    if (value == null) {
      throw new NullPointerException("notificationContentId is null");
    }
  }
}
