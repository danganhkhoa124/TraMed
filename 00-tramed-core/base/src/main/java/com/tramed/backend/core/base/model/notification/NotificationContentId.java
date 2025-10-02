package com.tramed.backend.core.base.model.notification;

import com.tramed.backend.core.base.exception.InvalidArgumentModelException;
import java.util.UUID;

/**
 * ID of notification content
 *
 * @param value UUID of notification content
 */
public record NotificationContentId(UUID value) {
  public NotificationContentId {
    if (value == null) {
      throw new InvalidArgumentModelException("notificationContentId is null");
    }
  }
}
