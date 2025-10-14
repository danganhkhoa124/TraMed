package com.tramed.backend.presentation.webapi.support;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationTestDataHelper {

  private static final String INSERT_NOTIFICATION =
      "INSERT INTO notification (notification_id, logic_del_flg) VALUES (:notificationId, :logicDeleted)";
  private static final String INSERT_NOTIFICATION_CONTENT =
      "INSERT INTO notification_content (" +
      "notification_content_id, notification_id, content, locale, created_date, created_by, update_date, update_by" +
      ") VALUES (:notificationContentId, :notificationId, :content, :locale, :createdDate, :createdBy, :updatedDate, :updatedBy)";

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public NotificationTestDataHelper(NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void persist(NotificationRow notification, List<NotificationContentRow> contents) {
    jdbcTemplate.update(
        INSERT_NOTIFICATION,
        Map.of(
            "notificationId", notification.notificationId(),
            "logicDeleted", notification.logicDeleted()));

    for (NotificationContentRow content : contents) {
      MapSqlParameterSource parameters =
          new MapSqlParameterSource()
              .addValue("notificationContentId", content.notificationContentId())
              .addValue("notificationId", content.notificationId())
              .addValue("content", content.content())
              .addValue("locale", content.locale())
              .addValue("createdDate", content.createdDate())
              .addValue("createdBy", content.createdBy())
              .addValue("updatedDate", content.updatedDate())
              .addValue("updatedBy", content.updatedBy());

      jdbcTemplate.update(INSERT_NOTIFICATION_CONTENT, parameters);
    }
  }

  public record NotificationRow(UUID notificationId, boolean logicDeleted) {}

  public record NotificationContentRow(
      UUID notificationContentId,
      UUID notificationId,
      String content,
      String locale,
      OffsetDateTime createdDate,
      UUID createdBy,
      OffsetDateTime updatedDate,
      UUID updatedBy) {

    public static NotificationContentRow create(
        UUID notificationContentId,
        UUID notificationId,
        String content,
        String locale,
        OffsetDateTime createdDate,
        UUID createdBy) {
      return new NotificationContentRow(
          notificationContentId, notificationId, content, locale, createdDate, createdBy, null, null);
    }
  }
}
