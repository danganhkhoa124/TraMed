package com.tramed.backend.presentation.webapi.security.jwt;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenStore {

  private static final String TOKEN_PREFIX = "auth:token:";

  private final StringRedisTemplate redisTemplate;

  public JwtTokenStore(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void store(String token, Duration ttl) {
    redisTemplate.opsForValue().set(buildKey(token), token, ttl);
  }

  public boolean exists(String token) {
    return redisTemplate.hasKey(buildKey(token));
  }

  public void remove(String token) {
    redisTemplate.delete(buildKey(token));
  }

  private String buildKey(String token) {
    return TOKEN_PREFIX + token;
  }
}
