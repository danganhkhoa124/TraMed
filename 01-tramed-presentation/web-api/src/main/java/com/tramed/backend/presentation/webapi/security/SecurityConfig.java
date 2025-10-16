package com.tramed.backend.presentation.webapi.security;

import com.tramed.backend.presentation.webapi.security.jwt.JwtAuthenticationEntryPoint;
import com.tramed.backend.presentation.webapi.security.jwt.JwtAuthenticationFilter;
import com.tramed.backend.presentation.webapi.security.jwt.JwtProperties;
import com.tramed.backend.presentation.webapi.security.user.AdminUserDetailsService;
import java.util.Arrays;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {"/tra-med-api/auth/login"};

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPoint authenticationEntryPoint;
  private final AdminUserDetailsService userDetailsService;
  private final boolean securityEnabled;

  public SecurityConfig(
      JwtAuthenticationFilter jwtAuthenticationFilter,
      JwtAuthenticationEntryPoint authenticationEntryPoint,
      AdminUserDetailsService userDetailsService,
      Environment environment) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.userDetailsService = userDetailsService;
    this.securityEnabled =
        Arrays.stream(environment.getActiveProfiles())
            .noneMatch(profile -> profile.equalsIgnoreCase("test"));
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    if (securityEnabled) {
      http.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
          .authorizeHttpRequests(
              auth ->
                  auth.requestMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated());
      http.authenticationProvider(authenticationProvider());
      http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    } else {
      http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    }

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }
}
