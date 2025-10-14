package com.tramed.backend.presentation.webapi.support;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.tramed.backend.presentation.webapi.WebApiApplication;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

@SpringBootTest(classes = WebApiApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

  @RegisterExtension
  protected static final WireMockExtension WIRE_MOCK_SERVER =
      WireMockExtension.newInstance().options(wireMockConfig().dynamicPort()).build();

  private DatabaseCleanup databaseCleanup;
  private MockMvc mockMvc;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  void setUpDependencies(DatabaseCleanup databaseCleanup, MockMvc mockMvc) {
    this.databaseCleanup = databaseCleanup;
    this.mockMvc = mockMvc;
  }

  @DynamicPropertySource
  static void registerWireMockBaseUrl(DynamicPropertyRegistry registry) {
    registry.add("wiremock.server.base-url", WIRE_MOCK_SERVER::baseUrl);
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
