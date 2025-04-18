package com.tramed.backend.infrastructure.mybatis.repository;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.core.base.pagination.PageRequest;
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
   * @return List of notification
   */
  public List<NotificationQueryResult> fetchNotificationLocaleVN(PageRequest pageRequest) {
    List<NotificationContentEntity> notificationContentEntities =
        notificationContentMapper.fetchNotificationContent(
            pageRequest.getOffset(), pageRequest.getPageSize(), Locale.VI_VN);

    return notificationContentEntities.isEmpty()
        ? Collections.emptyList()
        : notificationContentEntities.stream()
            .map(NotificationContentEntity::toNotificationQueryResult)
            .collect(Collectors.toList());
  }

  /**
   * Count the total number of notification content by locale
   *
   * @return the total number of notification content
   */
  public int countNotificationContent(Locale locale) {
    return notificationContentMapper.countNotificationContent(locale);
  }
}
