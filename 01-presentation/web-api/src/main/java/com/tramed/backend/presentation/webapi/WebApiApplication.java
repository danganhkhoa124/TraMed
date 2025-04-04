package com.tramed.backend.presentation.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tramed.backend")
public class WebApiApplication {
  public static void main(String[] args) {
    SpringApplication.run(WebApiApplication.class, args);
  }
}
