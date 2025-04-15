package com.tramed.backend.presentation.webapi.controller.notification;

import com.tramed.backend.applicationcore.systemmanagement.service.notification.NotificationService;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/tra-med-api/notification")
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping
  public List<NotificationQueryResult> fetchNotification() {
    return notificationService.fetchNotification();
  }
}
