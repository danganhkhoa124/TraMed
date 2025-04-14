package com.tramed.backend.core.base;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = BaseApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // turn off database
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // turn off database
class BaseApplicationTests {

  @Test
  void contextLoads() {}
}
