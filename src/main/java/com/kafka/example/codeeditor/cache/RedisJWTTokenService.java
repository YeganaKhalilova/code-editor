package com.kafka.example.codeeditor.cache;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisJWTTokenService {

  private static final String REFRESH_TOKEN_KEY_PREFIX = "refresh_token:";

  private final RedisTemplate<String, String> redisTemplate;

  public void storeToken(String email, String refreshToken, long ttl) {
    String redisKey = getRefreshTokenRedisKey(email);
    try {
      redisTemplate.opsForValue().set(redisKey, refreshToken, ttl, TimeUnit.MILLISECONDS);
    } catch (Exception e) {
      log.error("Failed to store token in Redis", e);
    }
  }

  public String getToken(String email) {
    String redisKey = REFRESH_TOKEN_KEY_PREFIX + email;
    var refreshToken = redisTemplate.opsForValue().get(redisKey);

    try {
      if (refreshToken == null) {
        log.error("Token not found for email: {}", email);
      }
    } catch (Exception e) {
      log.error("Failed to retrieve token in Redis", e);
    }
    return refreshToken;
  }

  public boolean validateRefreshToken(String email, String providedToken) {
    String storedToken = getToken(email);
    return storedToken != null && storedToken.equals(providedToken);
  }

  public void deleteRefreshToken(String email) {
    String redisKey = REFRESH_TOKEN_KEY_PREFIX + email;
    redisTemplate.delete(redisKey);
  }

  private String getRefreshTokenRedisKey(String email) {
    return REFRESH_TOKEN_KEY_PREFIX + email;
  }
}

