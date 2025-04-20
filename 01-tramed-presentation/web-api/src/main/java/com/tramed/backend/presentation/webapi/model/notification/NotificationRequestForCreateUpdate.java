package com.tramed.backend.presentation.webapi.model.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

/**
 * NotificationRequest For Create Or Update
 *
 * @param notificationId ID of Notification
 * @param content the content of notification
 * @param locale the locale of notification
 */
public record NotificationRequestForCreateUpdate(
    UUID notificationId,
    @Size(max = 255, message = "E0001") String content,
    @NotNull(message = "E0002") @NotBlank(message = "E0002") String locale) {}
