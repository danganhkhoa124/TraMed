package com.tramed.backend.infrastructure.mybatis.entity.notification;

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
    String locale,
    Instant createdDate,
    UUID createdBy) {}
