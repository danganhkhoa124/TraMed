package com.tramed.backend.infrastructure.mybatis.config;

import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import javax.sql.DataSource;
import org.assertj.db.type.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbSetupConfig {
  private final DataSource dataSource;

  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Bean
  public Source sourceTest() {
    return new Source(url, username, password);
  }

  @Autowired
  public DbSetupConfig(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public DataSourceDestination dataSourceDestination() {
    return new DataSourceDestination(dataSource);
  }

  @Bean
  public DbSetupTracker dbSetupTracker() {
    return new DbSetupTracker();
  }
}
