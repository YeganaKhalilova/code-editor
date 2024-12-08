package com.kafka.example.codeeditor.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthResponse {
  private String accessToken;
  private String refreshToken;
}
