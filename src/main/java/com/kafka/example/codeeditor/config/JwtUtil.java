package com.kafka.example.codeeditor.config;

import com.kafka.example.codeeditor.cache.RedisJWTTokenService;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
@RequiredArgsConstructor
public class JwtUtil {

  @Value("${security.key}")
  private String jwtKey;
  @Value ("${refresh-token.ttl}")
  private Long refreshTtl;
  @Value ("${access-token.ttl}")
  private Long accessTtl;

  private final RedisJWTTokenService jwtTokenService;

  public String generateAccessToken(String email, String role) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", role);
    return createToken(claims, email,accessTtl);
  }

  public String generateRefreshToken(String email) {
    Map<String, Object> claims = new HashMap<>();
    String refreshToken = createToken(claims, email, refreshTtl);
    jwtTokenService.storeToken(email, refreshToken, refreshTtl);
    return refreshToken;
  }

  private String createToken(Map<String, Object> claims, String email, long expiration) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(email)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration ))
        .signWith(SignatureAlgorithm.HS512,getSigninKey())
        .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String extractUserRole(String token) {
    Claims claims = extractAllClaim(token);
    return claims.get("role", String.class);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaim(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaim(String token) {
    return Jwts.parser()
        .setSigningKey(getSigninKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean validateToken(UserDetails user, String token) {
    String username= extractUsername(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }


  private Key getSigninKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
