package com.kafka.example.codeeditor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|email\\.ru|.*\\.edu)$",
      message = "Email must be a valid Gmail, Yahoo, email.ru, or educational domain")
  private String email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
      message = "Password must contain at least one letter and one digit")
  private String password;
}
