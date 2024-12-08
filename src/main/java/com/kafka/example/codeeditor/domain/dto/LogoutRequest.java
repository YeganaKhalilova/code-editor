package com.kafka.example.codeeditor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {

  @Email(message = "Email must be valid")
  @Pattern(
      regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|email\\.ru|.*\\.edu)$",
      message = "Email must be a valid Gmail, Yahoo, email.ru, or educational domain"
  )
  private String email;
  @NotBlank
  private String refreshToken;
}
