package com.tramed.backend.applicationcore.systemmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SystemManagementApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // turn off database
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) // turn off database
public class SystemManagementApplicationTests {
  @Test
  void contextLoads() {}
}
