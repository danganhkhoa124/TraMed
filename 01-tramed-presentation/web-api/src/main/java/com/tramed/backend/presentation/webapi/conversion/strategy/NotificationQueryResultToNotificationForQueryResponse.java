package com.tramed.backend.presentation.webapi.conversion.strategy;

import com.tramed.backend.core.base.conversion.ConversionStrategy;
import com.tramed.backend.core.base.model.notification.NotificationQueryResult;
import com.tramed.backend.presentation.webapi.model.notification.NotificationForQueryResponse;

public class NotificationQueryResultToNotificationForQueryResponse
    implements ConversionStrategy<NotificationQueryResult, NotificationForQueryResponse> {

  /**
   * Returns the class of the object to be converted.
   *
   * @return The class of the object to be converted
   */
  @Override
  public Class<NotificationQueryResult> getSourceClass() {
    return NotificationQueryResult.class;
  }

  /**
   * Returns the class of the converted object.
   *
   * @return The converted class
   */
  @Override
  public Class<NotificationForQueryResponse> getTargetClass() {
    return NotificationForQueryResponse.class;
  }

  /**
   * Converts the specified object.
   *
   * @param source The object to convert
   * @return The converted object
   */
  @Override
  public NotificationForQueryResponse convert(NotificationQueryResult source) {
    return new NotificationForQueryResponse(
        source.notificationContentId().value(),
        source.notificationId().value(),
        source.content(),
        source.locale().getValue(),
        source.createdBy().value(),
        source.createdDate());
  }
}
