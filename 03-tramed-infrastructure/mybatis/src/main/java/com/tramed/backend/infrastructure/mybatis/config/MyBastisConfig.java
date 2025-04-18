package com.tramed.backend.infrastructure.mybatis.config;

import com.tramed.backend.core.base.model.common.EnumDBColumn;
import com.tramed.backend.infrastructure.mybatis.typehandler.EnumValueJavaConventionTypeHandler;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(
    basePackages = "com.tramed.backend.infrastructure.mybatis.mapper",
    sqlSessionFactoryRef = "sqlSessionFactory")
public class MyBastisConfig {

  @Bean
  @ConfigurationProperties("spring.datasource")
  public DataSourceProperties dataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "dataSource")
  public DataSource dataSource() {
    return dataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean
  @Primary
  public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);

    // Register handler
    org.apache.ibatis.session.Configuration configuration =
        new org.apache.ibatis.session.Configuration();
    configuration
        .getTypeHandlerRegistry()
        .register(EnumDBColumn.class, EnumValueJavaConventionTypeHandler.class);
    sqlSessionFactoryBean.setConfiguration(configuration);
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }
}
