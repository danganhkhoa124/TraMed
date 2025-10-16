package com.tramed.backend.presentation.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tramed.backend")
public class WebApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebApiApplication.class, args);
  }
}
