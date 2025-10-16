package com.tramed.backend.presentation.webapi.security.jwt;

import com.tramed.backend.presentation.webapi.security.user.ApplicationUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer ";

  private final JwtTokenProvider tokenProvider;
  private final ApplicationUserDetailsService userDetailsService;

  public JwtAuthenticationFilter(
      JwtTokenProvider tokenProvider, ApplicationUserDetailsService userDetailsService) {
    this.tokenProvider = tokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    extractToken(request)
        .ifPresent(
            token -> {
              if (SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                  String username = tokenProvider.getUsernameFromToken(token);
                  var userDetails = userDetailsService.loadActiveUser(username);
                  if (tokenProvider.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                  }
                } catch (RuntimeException ex) {
                  SecurityContextHolder.clearContext();
                }
              }
            });

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith(BEARER_PREFIX)) {
      return Optional.of(header.substring(BEARER_PREFIX.length()));
    }
    return Optional.empty();
  }
}
