package com.tramed.backend.infrastructure.mybatis.entity.notification;

import java.util.UUID;

/**
 * Entity of notification
 *
 * @param notificationId ID of notification
 * @param logicDelFlg Delete logic notification
 */
public record NotificationEntity(UUID notificationId, boolean logicDelFlg) {}
