package com.tramed.backend.infrastructure.mybatis.mapper;

import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotificationContentMapper {

  /**
   * Fetch all notification content by locale
   *
   * @param locale Locale want to fetch all notification
   * @return List of NotificationContentEntity
   */
  List<NotificationContentEntity> fetchNotificationContentByLocale(@Param("locale") String locale);
}
