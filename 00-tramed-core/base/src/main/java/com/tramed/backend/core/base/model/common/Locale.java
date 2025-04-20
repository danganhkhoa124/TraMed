package com.tramed.backend.core.base.model.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Locale implements EnumDBColumn {
  EN_US("en-US"),
  VI_VN("vi-VN");

  private final String value;

  @Override
  public String value() {
    return value;
  }

  public static Locale fromValue(String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }

    for (Locale locale : values()) {
      if (locale.getValue().equalsIgnoreCase(value)) {
        return locale;
      }
    }
    throw new IllegalArgumentException("Invalid Locale: " + value);
  }
}
