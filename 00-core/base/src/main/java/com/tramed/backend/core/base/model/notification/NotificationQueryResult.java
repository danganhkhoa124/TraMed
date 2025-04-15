package com.tramed.backend.core.base.model.notification;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.common.UserId;
import java.time.Instant;

/**
 * Notification of query result
 *
 * @param notificationContentId ID of notification content
 * @param notificationId ID of notification
 * @param content content of notification
 * @param locale notification locale
 * @param createdBy who was created a notification content
 * @param createdDate when was created a notification content
 */
public record NotificationQueryResult(
    NotificationContentId notificationContentId,
    NotificationId notificationId,
    String content,
    Locale locale,
    UserId createdBy,
    Instant createdDate) {}
