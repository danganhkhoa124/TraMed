package com.tramed.backend.presentation.webapi.support;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleanup {

  private final JdbcTemplate jdbcTemplate;

  public DatabaseCleanup(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void clean() {
    jdbcTemplate.update("DELETE FROM notification_content");
    jdbcTemplate.update("DELETE FROM notification");
  }
}
