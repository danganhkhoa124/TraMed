package com.tramed.backend.core.base.model.notification;

import java.util.UUID;

/**
 * ID of notification content
 *
 * @param notificationContentId UUID of notification content
 */
public record NotificationContentId(UUID notificationContentId) {
  public NotificationContentId {
    if (notificationContentId == null) {
      throw new NullPointerException("notificationContentId is null");
    }
  }
}
