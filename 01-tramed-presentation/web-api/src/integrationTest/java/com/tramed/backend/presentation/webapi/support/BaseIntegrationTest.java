package com.tramed.backend.presentation.webapi.support;

import com.tramed.backend.presentation.webapi.WebApiApplication;
import com.tramed.backend.presentation.webapi.support.IntegrationTest;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

@IntegrationTest
@SpringBootTest(classes = WebApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

  private DatabaseCleanup databaseCleanup;
  private MockMvc mockMvc;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  void setUpDependencies(DatabaseCleanup databaseCleanup, MockMvc mockMvc) {
    this.databaseCleanup = databaseCleanup;
    this.mockMvc = mockMvc;
  }

  @BeforeEach
  void resetDatabase() {
    databaseCleanup.clean();
  }

  protected MockMvc mockMvc() {
    return mockMvc;
  }

  protected String readJson(String path) {
    try (InputStream inputStream = new ClassPathResource(path).getInputStream()) {
      return StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
    } catch (IOException exception) {
      throw new UncheckedIOException("Unable to read resource: " + path, exception);
    }
  }
}
