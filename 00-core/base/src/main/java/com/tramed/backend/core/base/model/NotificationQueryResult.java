package com.tramed.backend.core.base.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Notification of query result
 *
 * @param notification_content_id ID of notification content
 * @param notification_id ID of notification
 * @param content content of notification
 * @param locale notification locale
 * @param created_by who was created a notification content
 * @param created_date when was created a notification content
 */
public record NotificationQueryResult(
    UUID notification_content_id,
    UUID notification_id,
    String content,
    String locale,
    UUID created_by,
    Instant created_date) {}
