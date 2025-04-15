package com.tramed.backend.applicationcore.systemmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.tramed.backend.infrastructure.mybatis")
@MapperScan(basePackages = "com.tramed.backend.infrastructure.mybatis.mapper")
public class SystemManagementApplication {
  public static void main(String[] args) {
    SpringApplication.run(SystemManagementApplication.class, args);
  }
}
