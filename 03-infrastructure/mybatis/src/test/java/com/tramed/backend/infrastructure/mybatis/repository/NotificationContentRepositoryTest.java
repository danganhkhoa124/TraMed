package com.tramed.backend.infrastructure.mybatis.repository;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.operation.Operation;
import com.tramed.backend.infrastructure.mybatis.config.DbSetupConfig;
import com.tramed.backend.infrastructure.mybatis.entity.notification.NotificationContentEntity;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=test")
@ActiveProfiles("test")
@DisplayName("NotificationContentRepository")
public class NotificationContentRepositoryTest {
  @Autowired private NotificationContentRepository notificationContentRepository;

  @Autowired private DbSetupConfig testDbSetupConfig;

  private static final Operation DELETE_ALL = deleteAllFrom();

  @BeforeEach
  void beforeEach() {
    testDbSetupConfig
        .dbSetupTracker()
        .launchIfNecessary(new DbSetup(testDbSetupConfig.dataSourceDestination(), DELETE_ALL));
  }

  @AfterEach
  void afterEach() {
    testDbSetupConfig
        .dbSetupTracker()
        .launchIfNecessary(new DbSetup(testDbSetupConfig.dataSourceDestination(), DELETE_ALL));
  }

  @Nested
  @DisplayName("FetchNotificationContentByLocale")
  class FetchNotificationContentByLocale {
    @Test
    @DisplayName("Should not throw error")
    void success() {
      List<NotificationContentEntity> results =
          notificationContentRepository.fetchNotificationContentByLocale("");

      Assertions.assertThat(results).isNotNull();
      results.forEach(System.out::println);
      // run docker for junit
    }
  }
}
