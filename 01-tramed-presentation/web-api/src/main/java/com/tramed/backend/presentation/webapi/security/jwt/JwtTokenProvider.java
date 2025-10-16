package com.tramed.backend.presentation.webapi.security.jwt;

import com.tramed.backend.presentation.webapi.security.user.AdminUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private final JwtProperties properties;
  private final Key signingKey;

  public JwtTokenProvider(JwtProperties properties) {
    this.properties = properties;
    if (properties.secret() == null || properties.secret().isBlank()) {
      throw new IllegalStateException("JWT secret must be configured");
    }
    if (properties.expirationInMinutes() <= 0) {
      throw new IllegalStateException("JWT expiration must be a positive value");
    }
    this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.secret()));
  }

  public String generateToken(AdminUserDetails userDetails) {
    Instant now = Instant.now();
    Instant expiry = now.plus(properties.expirationInMinutes(), ChronoUnit.MINUTES);

    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .claim("uid", userDetails.getUserId().value().toString())
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expiry))
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    return parseClaims(token).getSubject();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    Claims claims = parseClaims(token);
    String username = claims.getSubject();
    Date expiration = claims.getExpiration();
    return username.equals(userDetails.getUsername()) && expiration.after(new Date());
  }

  private Claims parseClaims(String token) {
    return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token).getPayload();
  }
}
