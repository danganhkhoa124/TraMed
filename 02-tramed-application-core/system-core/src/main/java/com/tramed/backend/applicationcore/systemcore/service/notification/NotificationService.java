package com.tramed.backend.applicationcore.systemcore.service.notification;

import com.fasterxml.uuid.Generators;
import com.tramed.backend.applicationcore.systemcore.service.BaseService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService extends BaseService {

  private final NotificationContentRepository notificationContentRepository;

  public NotificationService(
      NotificationContentRepository notificationContentRepository,
      AuthenticatedUserProvider authenticatedUserProvider) {
    super(authenticatedUserProvider);
    this.notificationContentRepository = notificationContentRepository;
  }

  /**
   * Fetch list of notification with locale VN
   *
   * @param locale notification locale
   * @param pageRequest The pageRequest for fetch notification list with locale VN
   * @return list of notification
   */
  @Transactional(readOnly = true)
  public PageModel<NotificationQueryResult> fetchNotification(
      Locale locale, PageRequest pageRequest) {
    List<NotificationQueryResult> records =
        notificationContentRepository.fetchNotificationByLocale(locale, pageRequest);

    Long totalCount = (long) notificationContentRepository.countNotificationContent(locale);

    int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

    return new PageModel<>(
        totalCount, pageRequest.getPageSize(), totalPages, pageRequest.getPageNumber(), records);
  }

  /**
   * Create a new notification
   *
   * @param notificationForCreateUpdate NotificationForCreateUpdate
   */
  @Transactional
  public void insertNotification(NotificationForCreateUpdate notificationForCreateUpdate) {
    UserId userId = requireCurrentUserId();
    Locale locale = notificationForCreateUpdate.locale();
    NotificationId notificationId = notificationForCreateUpdate.notificationId();
    if (notificationId == null) {
      notificationId = new NotificationId(Generators.timeBasedEpochGenerator().generate());
      notificationContentRepository.insertNotification(
          new NotificationEntity(notificationId.value(), false));
    } else {
      if (!notificationContentRepository.existsByNotificationId(notificationId.value())) {
        throw new NotFoundResourceException(
            "Notification does not exist: " + notificationId.value());
      }

      if (notificationContentRepository.checkDuplicateNotificationContent(
          locale, notificationId.value())) {
        throw new PreconditionException(
            "Duplicate notification id: " + notificationId.value() + " and locale: " + locale);
      }
    }

    NotificationContentId notificationContentId =
        new NotificationContentId(Generators.timeBasedEpochGenerator().generate());
    NotificationContentEntity notificationContentEntity =
        new NotificationContentEntity(
            notificationContentId.value(),
            notificationId.value(),
            notificationForCreateUpdate.content(),
            locale,
            Instant.now(),
            userId.value(),
            null,
            null);
    notificationContentRepository.insertNotificationContent(notificationContentEntity);
  }
}
