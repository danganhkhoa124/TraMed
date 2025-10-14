package com.tramed.backend.presentation.webapi.controller.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tramed.backend.presentation.webapi.support.BaseIntegrationTest;
import com.tramed.backend.presentation.webapi.support.NotificationTestDataHelper;
import com.tramed.backend.presentation.webapi.support.NotificationTestDataHelper.NotificationContentRow;
import com.tramed.backend.presentation.webapi.support.NotificationTestDataHelper.NotificationRow;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public class NotificationControllerListIT extends BaseIntegrationTest {

  private static final String BASE_URL = "/tra-med-api/notification/list";
  private static final UUID CREATOR_ID =
      UUID.fromString("55555555-5555-5555-5555-555555555555");
  private static final UUID FIRST_NOTIFICATION_ID =
      UUID.fromString("11111111-1111-1111-1111-111111111111");
  private static final UUID SECOND_NOTIFICATION_ID =
      UUID.fromString("22222222-2222-2222-2222-222222222222");
  private static final UUID LOGIC_DELETED_NOTIFICATION_ID =
      UUID.fromString("33333333-3333-3333-3333-333333333333");
  private static final UUID FIRST_CONTENT_ID =
      UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1");
  private static final UUID SECOND_CONTENT_ID =
      UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbb2");
  private static final UUID HIDDEN_CONTENT_ID =
      UUID.fromString("cccccccc-cccc-cccc-cccc-ccccccccccc3");

  @Autowired private MockMvc mockMvc;
  @Autowired private NotificationTestDataHelper notificationTestDataHelper;

  @Test
  void shouldReturnNotificationsForVietnameseLocale() throws Exception {
    insertNotificationFixtures();

    String requestBody = readJson("request/notification/list/with-locale-vi.json");
    String expectedResponse = readJson("response/notification/list/with-locale-vi.json");

    mockMvc
        .perform(
            post(BASE_URL)
                .param("locale", "vi-VN")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse, true));
  }

  @Test
  void shouldReturnEmptyPageWhenLocaleHasNoData() throws Exception {
    insertNotificationFixtures();

    String requestBody = readJson("request/notification/list/with-locale-en.json");
    String expectedResponse = readJson("response/notification/list/with-locale-en.json");

    mockMvc
        .perform(
            post(BASE_URL)
                .param("locale", "en-US")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse, true));
  }

  private void insertNotificationFixtures() {
    notificationTestDataHelper.persist(
        new NotificationRow(FIRST_NOTIFICATION_ID, false),
        List.of(
            NotificationContentRow.create(
                FIRST_CONTENT_ID,
                FIRST_NOTIFICATION_ID,
                "Thông báo lịch khám định kỳ",
                "vi-VN",
                OffsetDateTime.parse("2024-01-01T08:00:00Z"),
                CREATOR_ID)));

    notificationTestDataHelper.persist(
        new NotificationRow(SECOND_NOTIFICATION_ID, false),
        List.of(
            NotificationContentRow.create(
                SECOND_CONTENT_ID,
                SECOND_NOTIFICATION_ID,
                "Cập nhật tính năng mới",
                "vi-VN",
                OffsetDateTime.parse("2024-01-02T09:30:00Z"),
                CREATOR_ID)));

    notificationTestDataHelper.persist(
        new NotificationRow(LOGIC_DELETED_NOTIFICATION_ID, true),
        List.of(
            NotificationContentRow.create(
                HIDDEN_CONTENT_ID,
                LOGIC_DELETED_NOTIFICATION_ID,
                "Dữ liệu bị ẩn do thông báo đã xóa",
                "vi-VN",
                OffsetDateTime.parse("2024-01-03T10:00:00Z"),
                CREATOR_ID)));
  }
}
