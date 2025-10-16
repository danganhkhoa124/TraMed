package com.tramed.backend.applicationcore.systemcore.service.notification;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tramed.backend.core.base.exception.NotFoundResourceException;
import com.tramed.backend.core.base.exception.PreconditionException;
import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.notification.NotificationContentId;
import com.tramed.backend.core.base.model.notification.NotificationForCreateUpdate;
import com.tramed.backend.core.base.model.notification.NotificationId;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.core.base.pagination.PageModel;
import com.tramed.backend.core.base.pagination.PageRequest;
import com.tramed.backend.core.base.security.AuthenticatedUserProvider;
import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationEntity;
import com.tramed.backend.infrastructure.mybatis.repository.NotificationContentRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

  @Mock private NotificationContentRepository notificationContentRepository;
  @Mock private AuthenticatedUserProvider authenticatedUserProvider;

  private NotificationService notificationService;

  @BeforeEach
  void setUp() {
    notificationService =
        new NotificationService(notificationContentRepository, authenticatedUserProvider);
    lenient()
        .when(authenticatedUserProvider.getCurrentUserId())
        .thenReturn(Optional.of(new UserId(UUID.fromString("22222222-2222-2222-2222-222222222222"))));
  }

  @Test
  void fetchNotification_shouldReturnPageModel() {
    PageRequest pageRequest = new PageRequest(2, 2, null);
    List<NotificationQueryResult> records =
        List.of(
            new NotificationQueryResult(
                new NotificationContentId(UUID.randomUUID()),
                new NotificationId(UUID.randomUUID()),
                "Content 1",
                Locale.VI_VN,
                new UserId(UUID.randomUUID()),
                Instant.parse("2024-01-01T10:15:30.00Z"),
                null,
                null));

    when(notificationContentRepository.fetchNotificationByLocale(Locale.VI_VN, pageRequest))
        .thenReturn(records);
    when(notificationContentRepository.countNotificationContent(Locale.VI_VN)).thenReturn(5);

    PageModel<NotificationQueryResult> result =
        notificationService.fetchNotification(Locale.VI_VN, pageRequest);

    assertThat(result.getTotal()).isEqualTo(5L);
    assertThat(result.getLimit()).isEqualTo(2);
    assertThat(result.getTotalPage()).isEqualTo(3);
    assertThat(result.getPage()).isEqualTo(2);
    assertThat(result.getRecords()).containsExactlyElementsOf(records);
  }

  @Test
  void insertNotification_shouldCreateNewNotificationWhenIdIsNull() {
    NotificationForCreateUpdate request =
        new NotificationForCreateUpdate(null, "New content", Locale.VI_VN);

    notificationService.insertNotification(request);

    ArgumentCaptor<NotificationEntity> notificationEntityCaptor =
        ArgumentCaptor.forClass(NotificationEntity.class);
    verify(notificationContentRepository).insertNotification(notificationEntityCaptor.capture());
    NotificationEntity notificationEntity = notificationEntityCaptor.getValue();
    assertThat(notificationEntity.notificationId()).isNotNull();
    assertThat(notificationEntity.logicDelFlg()).isFalse();

    ArgumentCaptor<NotificationContentEntity> contentEntityCaptor =
        ArgumentCaptor.forClass(NotificationContentEntity.class);
    verify(notificationContentRepository).insertNotificationContent(contentEntityCaptor.capture());
    NotificationContentEntity contentEntity = contentEntityCaptor.getValue();
    assertThat(contentEntity.notificationContentId()).isNotNull();
    assertThat(contentEntity.notificationId()).isEqualTo(notificationEntity.notificationId());
    assertThat(contentEntity.content()).isEqualTo("New content");
    assertThat(contentEntity.locale()).isEqualTo(Locale.VI_VN);
    assertThat(contentEntity.createdBy())
        .isEqualTo(UUID.fromString("22222222-2222-2222-2222-222222222222"));
    assertThat(contentEntity.createdDate()).isNotNull();
  }

  @Test
  void insertNotification_shouldCreateContentForExistingNotification() {
    UUID existingNotificationId = UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    NotificationForCreateUpdate request =
        new NotificationForCreateUpdate(
            new NotificationId(existingNotificationId), "Updated", Locale.VI_VN);

    when(notificationContentRepository.existsByNotificationId(existingNotificationId))
        .thenReturn(true);
    when(notificationContentRepository.checkDuplicateNotificationContent(
            Locale.VI_VN, existingNotificationId))
        .thenReturn(false);

    notificationService.insertNotification(request);

    verify(notificationContentRepository, never())
        .insertNotification(org.mockito.ArgumentMatchers.any());
    ArgumentCaptor<NotificationContentEntity> contentEntityCaptor =
        ArgumentCaptor.forClass(NotificationContentEntity.class);
    verify(notificationContentRepository).insertNotificationContent(contentEntityCaptor.capture());
    NotificationContentEntity contentEntity = contentEntityCaptor.getValue();
    assertThat(contentEntity.notificationContentId()).isNotNull();
    assertThat(contentEntity.notificationId()).isEqualTo(existingNotificationId);
  }

  @Test
  void insertNotification_shouldThrowWhenNotificationDoesNotExist() {
    UUID notificationId = UUID.fromString("99999999-9999-9999-9999-999999999999");
    NotificationForCreateUpdate request =
        new NotificationForCreateUpdate(
            new NotificationId(notificationId), "Content", Locale.VI_VN);

    when(notificationContentRepository.existsByNotificationId(notificationId)).thenReturn(false);

    assertThatThrownBy(() -> notificationService.insertNotification(request))
        .isInstanceOf(NotFoundResourceException.class)
        .hasMessageContaining(notificationId.toString());

    verify(notificationContentRepository, never())
        .checkDuplicateNotificationContent(Locale.VI_VN, notificationId);
    verify(notificationContentRepository, never())
        .insertNotificationContent(org.mockito.ArgumentMatchers.any());
    verify(notificationContentRepository, never())
        .insertNotification(org.mockito.ArgumentMatchers.any());
  }

  @Test
  void insertNotification_shouldThrowWhenDuplicateContentExists() {
    UUID notificationId = UUID.fromString("44444444-4444-4444-4444-444444444444");
    NotificationForCreateUpdate request =
        new NotificationForCreateUpdate(
            new NotificationId(notificationId), "Content", Locale.VI_VN);

    when(notificationContentRepository.existsByNotificationId(notificationId)).thenReturn(true);
    when(notificationContentRepository.checkDuplicateNotificationContent(
            Locale.VI_VN, notificationId))
        .thenReturn(true);

    assertThatThrownBy(() -> notificationService.insertNotification(request))
        .isInstanceOf(PreconditionException.class)
        .hasMessageContaining(notificationId.toString());

    verify(notificationContentRepository, never())
        .insertNotificationContent(org.mockito.ArgumentMatchers.any());
    verify(notificationContentRepository, never())
        .insertNotification(org.mockito.ArgumentMatchers.any());
  }
}
