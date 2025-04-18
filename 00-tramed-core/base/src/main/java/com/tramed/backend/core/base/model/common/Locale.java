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
}
