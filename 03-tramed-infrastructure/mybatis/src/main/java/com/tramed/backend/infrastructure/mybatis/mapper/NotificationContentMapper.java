package com.tramed.backend.infrastructure.mybatis.mapper;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationContentMapper {

  /**
   * Fetch all notification content by locale
   *
   * @param offset The offset for pagination.
   * @param limit The limit for pagination.
   * @param locale The locale of notification
   * @return List of NotificationContentEntity
   */
  List<NotificationContentEntity> fetchNotificationContent(
      @Param("offset") int offset, @Param("limit") int limit, @Param("locale") Locale locale);

  /**
   * Count the total number of notification content by locale
   *
   * @param locale The locale of notification
   * @return the total number of notification content
   */
  int countNotificationContent(@Param("locale") Locale locale);
}
