package com.tramed.backend.presentation.webapi.model.notification;

import java.time.Instant;
import java.util.UUID;

/**
 * @param notificationContentId ID of notification content
 * @param notificationId ID of notification
 * @param content content of notification
 * @param locale locale en_US/vi_VN
 * @param createdBy date of create the notification content
 * @param createdDate user create this notification content
 * @param updatedBy date of update the notification content
 * @param updatedDate user update this notification content
 */
public record NotificationForQueryResponse(
    UUID notificationContentId,
    UUID notificationId,
    String content,
    String locale,
    UUID createdBy,
    Instant createdDate,
    UUID updatedBy,
    Instant updatedDate) {}
