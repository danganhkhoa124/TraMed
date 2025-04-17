package com.tramed.backend.presentation.webapi.controller.notification;

import com.tramed.backend.applicationcore.systemmanagement.service.notification.NotificationService;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.core.base.pagination.PageModel;
import com.tramed.backend.core.base.pagination.PageRequest;
import com.tramed.backend.presentation.webapi.conversion.Converter;
import com.tramed.backend.presentation.webapi.model.common.PageableRequest;
import com.tramed.backend.presentation.webapi.model.notification.NotificationForQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tra-med-api/notification")
public class NotificationController {

  private final NotificationService notificationService;

  /**
   * Fetch list of notification with locale VN
   *
   * @param request The pageRequest for fetch notification list with locale VN
   * @return list of notification {@link PageModel<NotificationForQueryResponse>}
   */
  @PostMapping("/list-vn")
  public ResponseEntity<PageModel<NotificationForQueryResponse>> fetchNotificationLocaleVN(
      @RequestBody PageableRequest request) {
    PageModel<NotificationQueryResult> result =
        notificationService.fetchNotificationLocaleVN(
            Converter.convert(request, PageRequest.class));
    return ResponseEntity.ok(
        new PageModel<>(
            result.getTotal(),
            result.getLimit(),
            result.getTotalPage(),
            result.getPage(),
            result.getRecords().stream()
                .map(item -> Converter.convert(item, NotificationForQueryResponse.class))
                .toList()));
  }
}
