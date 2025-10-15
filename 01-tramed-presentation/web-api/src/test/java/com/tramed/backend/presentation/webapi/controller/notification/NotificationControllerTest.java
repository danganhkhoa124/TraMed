package com.tramed.backend.presentation.webapi.controller.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.tramed.backend.applicationcore.systemcore.service.notification.NotificationService;
import com.tramed.backend.core.base.exception.PreconditionException;
import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.notification.NotificationContentId;
import com.tramed.backend.core.base.model.notification.NotificationForCreateUpdate;
import com.tramed.backend.core.base.model.notification.NotificationId;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.core.base.pagination.PageModel;
import com.tramed.backend.core.base.pagination.PageRequest;
import com.tramed.backend.presentation.webapi.model.common.PageableRequest;
import com.tramed.backend.presentation.webapi.model.notification.NotificationForQueryResponse;
import com.tramed.backend.presentation.webapi.model.notification.NotificationRequestForCreateUpdate;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

  @Mock private NotificationService notificationService;

  private NotificationController notificationController;

  @BeforeEach
  void setUp() {
    notificationController = new NotificationController(notificationService);
  }

  @Test
  void fetchNotifications_shouldReturnConvertedPage_whenLocaleProvided() {
    PageableRequest request = new PageableRequest("2", "5");
    PageRequest expectedPageRequest = new PageRequest(2, 5, Collections.emptyList());

    NotificationContentId notificationContentId = new NotificationContentId(UUID.randomUUID());
    NotificationId notificationId = new NotificationId(UUID.randomUUID());
    UserId createdBy = new UserId(UUID.randomUUID());
    Instant createdDate = Instant.now();
    NotificationQueryResult queryResult =
        new NotificationQueryResult(
            notificationContentId,
            notificationId,
            "New appointment schedule",
            Locale.VI_VN,
            createdBy,
            createdDate,
            null,
            null);

    PageModel<NotificationQueryResult> serviceResponse =
        new PageModel<>(1L, 5, 1, 2, List.of(queryResult));

    when(notificationService.fetchNotification(Locale.VI_VN, expectedPageRequest))
        .thenReturn(serviceResponse);

    ResponseEntity<PageModel<NotificationForQueryResponse>> response =
        notificationController.fetchNotifications("vi-VN", request);

    NotificationForQueryResponse expectedRecord =
        new NotificationForQueryResponse(
            notificationContentId.value(),
            notificationId.value(),
            "New appointment schedule",
            Locale.VI_VN.getValue(),
            createdBy.value(),
            createdDate,
            null,
            null);

    PageModel<NotificationForQueryResponse> expectedResponse =
        new PageModel<>(1L, 5, 1, 2, List.of(expectedRecord));

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedResponse);
    verify(notificationService).fetchNotification(Locale.VI_VN, expectedPageRequest);
  }

  @Test
  void fetchNotifications_shouldReturnPage_whenLocaleMissing() {
    PageableRequest request = new PageableRequest(null, "10");
    PageRequest expectedPageRequest = new PageRequest(1, 10, Collections.emptyList());
    PageModel<NotificationQueryResult> serviceResponse =
        new PageModel<>(0L, 10, 0, 1, Collections.emptyList());

    when(notificationService.fetchNotification(null, expectedPageRequest))
        .thenReturn(serviceResponse);

    ResponseEntity<PageModel<NotificationForQueryResponse>> response =
        notificationController.fetchNotifications(null, request);

    PageModel<NotificationForQueryResponse> expectedResponse =
        new PageModel<>(0L, 10, 0, 1, Collections.emptyList());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expectedResponse);
    verify(notificationService).fetchNotification(null, expectedPageRequest);
  }

  @Test
  void fetchNotifications_shouldThrowException_whenLocaleInvalid() {
    PageableRequest request = new PageableRequest("1", "10");

    assertThatThrownBy(() -> notificationController.fetchNotifications("invalid", request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Invalid Locale: invalid");

    verifyNoInteractions(notificationService);
  }

  @Test
  void createNotification_shouldDelegateToService_whenNotificationIdPresent() {
    UUID notificationId = UUID.randomUUID();
    NotificationRequestForCreateUpdate request =
        new NotificationRequestForCreateUpdate(
            notificationId.toString(), "Reminder", Locale.EN_US.getValue());

    NotificationForCreateUpdate expectedRequest =
        new NotificationForCreateUpdate(
            new NotificationId(notificationId), "Reminder", Locale.EN_US);

    ResponseEntity<?> response = notificationController.createNotification(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNull();
    verify(notificationService).insertNotification(expectedRequest);
  }

  @Test
  void createNotification_shouldSupportNewNotification_whenNotificationIdMissing() {
    NotificationRequestForCreateUpdate request =
        new NotificationRequestForCreateUpdate(null, "Reminder", Locale.EN_US.getValue());

    NotificationForCreateUpdate expectedRequest =
        new NotificationForCreateUpdate(null, "Reminder", Locale.EN_US);

    ResponseEntity<?> response = notificationController.createNotification(request);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNull();
    verify(notificationService).insertNotification(expectedRequest);
  }

  @Test
  void createNotification_shouldPropagateException_whenServiceThrows() {
    UUID notificationId = UUID.randomUUID();
    NotificationRequestForCreateUpdate request =
        new NotificationRequestForCreateUpdate(
            notificationId.toString(), "Reminder", Locale.EN_US.getValue());

    NotificationForCreateUpdate expectedRequest =
        new NotificationForCreateUpdate(
            new NotificationId(notificationId), "Reminder", Locale.EN_US);

    doThrow(new PreconditionException("duplicate"))
        .when(notificationService)
        .insertNotification(expectedRequest);

    assertThatThrownBy(() -> notificationController.createNotification(request))
        .isInstanceOf(PreconditionException.class)
        .hasMessage("duplicate");

    verify(notificationService).insertNotification(expectedRequest);
  }
}
