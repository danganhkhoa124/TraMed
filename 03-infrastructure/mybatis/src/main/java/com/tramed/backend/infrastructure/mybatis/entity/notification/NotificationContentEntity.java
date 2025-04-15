package com.tramed.backend.infrastructure.mybatis.entity.notification;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.notification.NotificationContentId;
import com.tramed.backend.core.base.model.notification.NotificationId;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import java.time.Instant;
import java.util.UUID;

/**
 * Entity of notification content
 *
 * @param notificationContentId ID of notification content
 * @param notificationId ID of notification
 * @param content content of notification
 * @param locale locale en_US/vi_VN
 * @param createdDate date of create the notification content
 * @param createdBy user create this notification content
 */
public record NotificationContentEntity(
    UUID notificationContentId,
    UUID notificationId,
    String content,
    Locale locale,
    Instant createdDate,
    UUID createdBy) {

  /**
   * Build NotificationQueryResult from entity
   *
   * @return NotificationQueryResult
   */
  public NotificationQueryResult toNotificationQueryResult() {
    return new NotificationQueryResult(
        new NotificationContentId(notificationContentId),
        new NotificationId(notificationId),
        content,
        locale,
        new UserId(createdBy),
        createdDate);
  }
}
