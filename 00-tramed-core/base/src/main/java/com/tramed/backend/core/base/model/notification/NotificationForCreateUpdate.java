package com.tramed.backend.core.base.model.notification;

import com.tramed.backend.core.base.model.common.Locale;

/**
 * Notification For Create Or Update
 *
 * @param notificationId ID of Notification
 * @param content the content of notification
 * @param locale the locale of notification
 */
public record NotificationForCreateUpdate(
    NotificationId notificationId, String content, Locale locale) {}
