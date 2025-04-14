package com.tramed.backend.applicationcore.systemmanagement.service.notification;

import com.tramed.backend.core.base.model.NotificationQueryResult;
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
            UUID.randomUUID(),
            UUID.randomUUID(),
            "test",
            "vi en",
            UUID.randomUUID(),
            Instant.now()));
  }
}
