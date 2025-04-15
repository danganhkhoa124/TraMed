package com.tramed.backend.applicationcore.systemmanagement.service.notification;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.notification.NotificationContentId;
import com.tramed.backend.core.base.model.notification.NotificationId;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
  public List<NotificationQueryResult> fetchNotification() {
    return List.of(
        new NotificationQueryResult(
            new NotificationContentId(UUID.randomUUID()),
            new NotificationId(UUID.randomUUID()),
            "test",
            Locale.VI_VN,
            new UserId(UUID.randomUUID()),
            Instant.now()));
  }
}
