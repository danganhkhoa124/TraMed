package com.tramed.backend.core.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UtilsApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // turn off database
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // turn off database
class UtilsApplicationTests {

  @Test
  void contextLoads() {}
}
