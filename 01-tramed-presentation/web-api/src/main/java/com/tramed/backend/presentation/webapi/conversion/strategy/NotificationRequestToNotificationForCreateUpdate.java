package com.tramed.backend.presentation.webapi.conversion.strategy;

import com.tramed.backend.core.base.conversion.ConversionStrategy;
import com.tramed.backend.core.base.model.common.Locale;
import com.tramed.backend.core.base.model.notification.NotificationForCreateUpdate;
import com.tramed.backend.core.base.model.notification.NotificationId;
import com.tramed.backend.core.utils.common.ConvertUtils;
import com.tramed.backend.presentation.webapi.model.notification.NotificationRequestForCreateUpdate;

public class NotificationRequestToNotificationForCreateUpdate
    implements ConversionStrategy<NotificationRequestForCreateUpdate, NotificationForCreateUpdate> {

  /**
   * Returns the class of the object to be converted.
   *
   * @return The class of the object to be converted
   */
  @Override
  public Class<NotificationRequestForCreateUpdate> getSourceClass() {
    return NotificationRequestForCreateUpdate.class;
  }

  /**
   * Returns the class of the converted object.
   *
   * @return The converted class
   */
  @Override
  public Class<NotificationForCreateUpdate> getTargetClass() {
    return NotificationForCreateUpdate.class;
  }

  /**
   * Converts the specified object.
   *
   * @param source The object to convert
   * @return The converted object
   */
  @Override
  public NotificationForCreateUpdate convert(NotificationRequestForCreateUpdate source) {
    return new NotificationForCreateUpdate(
        ConvertUtils.convertStringToObjectUUID(source.notificationId(), NotificationId.class),
        source.content(),
        Locale.fromValue(source.locale()));
  }
}
