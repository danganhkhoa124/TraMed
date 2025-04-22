package com.tramed.backend.core.utils.common;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.UUID;

public class ConvertUtils {
  /**
   * Convert a String get to Object contain an attribute type UUID dynamically
   *
   * @param strUuid String source. Value will be assigned for value of class
   * @param clazz Object contain one attribute type UUID
   * @return Object contain attribute with type UUID
   */
  public static <T> T convertStringToObjectUUID(String strUuid, Class<T> clazz) {
    try {
      UUID uuid = toUUID(strUuid);

      if (Objects.isNull(uuid)) {
        return null;
      }
      Constructor<T> constructor = clazz.getConstructor(UUID.class);
      return constructor.newInstance(uuid);
    } catch (Exception e) {
      return null;
    }
  }

  // Convert String to UUID
  public static UUID toUUID(String data) {
    if (data == null) {
      return null;
    }
    try {
      return UUID.fromString(data);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
