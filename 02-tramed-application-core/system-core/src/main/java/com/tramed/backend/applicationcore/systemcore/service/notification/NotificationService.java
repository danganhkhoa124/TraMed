package com.tramed.backend.applicationcore.systemcore.service.notification;

import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.core.base.pagination.PageModel;
import com.tramed.backend.core.base.pagination.PageRequest;
import com.tramed.backend.infrastructure.mybatis.repository.NotificationContentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationContentRepository notificationContentRepository;

  /**
   * Fetch list of notification with locale VN
   *
   * @param pageRequest The pageRequest for fetch notification list with locale VN
   * @return list of notification
   */
  @Transactional(readOnly = true)
  public PageModel<NotificationQueryResult> fetchNotificationLocaleVN(PageRequest pageRequest) {
    List<NotificationQueryResult> records =
        notificationContentRepository.fetchNotificationLocaleVN(pageRequest);

    Long totalCount = (long) notificationContentRepository.countNotificationContent(Locale.VI_VN);

    int totalPages = (int) Math.ceil((double) totalCount / pageRequest.getPageSize());

    return new PageModel<>(
        totalCount, pageRequest.getPageSize(), totalPages, pageRequest.getPageNumber(), records);
  }
}
