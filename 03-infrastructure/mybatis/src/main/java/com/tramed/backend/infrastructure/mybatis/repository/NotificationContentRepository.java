package com.tramed.backend.infrastructure.mybatis.repository;

import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import com.tramed.backend.infrastructure.mybatis.mapper.NotificationContentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/** Repository class for handling the notification in system */
@Repository
@RequiredArgsConstructor
@Slf4j
public class NotificationContentRepository {
  private final NotificationContentMapper notificationContentMapper;

  /**
   * Fetch all notification in system
   *
   * @param locale Locale of notification
   * @return List of notification
   */
  public List<NotificationContentEntity> fetchNotificationContentByLocale(String locale) {
    return notificationContentMapper.fetchNotificationContentByLocale(locale);
  }
}
