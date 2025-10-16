package com.tramed.backend.core.base.security;

public interface PasswordHasher {

  String encode(String rawPassword);
}
