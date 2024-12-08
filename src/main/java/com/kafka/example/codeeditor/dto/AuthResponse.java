package com.kafka.example.codeeditor.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
}
