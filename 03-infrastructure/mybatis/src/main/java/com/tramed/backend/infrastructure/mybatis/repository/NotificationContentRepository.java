package com.tramed.backend.infrastructure.mybatis.repository;

import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import com.tramed.backend.infrastructure.mybatis.mapper.NotificationContentMapper;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
  public List<NotificationQueryResult> fetchNotificationContentByLocale(String locale) {
    List<NotificationContentEntity> notificationContentEntities =
        notificationContentMapper.fetchNotificationContentByLocale(locale);

    return notificationContentEntities.isEmpty()
        ? Collections.emptyList()
        : notificationContentEntities.stream()
            .map(NotificationContentEntity::toNotificationQueryResult)
            .collect(Collectors.toList());
  }
}
