package com.tramed.backend.presentation.webapi.model.notification;

import java.time.Instant;
import java.util.UUID;

public record NotificationForQueryResponse(
    UUID notificationContentId,
    UUID notificationId,
    String content,
    String locale,
    UUID createdBy,
    Instant createdDate) {}
