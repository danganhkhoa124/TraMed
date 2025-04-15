package com.tramed.backend.applicationcore.systemmanagement.service.notification;

import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.infrastructure.mybatis.repository.NotificationContentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationContentRepository notificationContentRepository;

  public List<NotificationQueryResult> fetchNotification() {
    return notificationContentRepository.fetchNotificationContentByLocale("");
  }
}
