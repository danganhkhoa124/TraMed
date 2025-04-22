package com.tramed.backend.presentation.webapi.model.notification;

import com.tramed.backend.core.utils.constants.ValidateConstant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * NotificationRequest For Create Or Update
 *
 * @param notificationId ID of Notification
 * @param content the content of notification
 * @param locale the locale of notification
 */
public record NotificationRequestForCreateUpdate(
    @Pattern(regexp = ValidateConstant.UUID_PATTERN_STR, message = "E0003") String notificationId,
    @Size(max = 255, message = "E0001") String content,
    @NotNull(message = "E0002") @NotBlank(message = "E0002") String locale) {}
