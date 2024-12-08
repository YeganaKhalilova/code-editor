package com.kafka.example.codeeditor.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Setter
@Getter
public class RegisterRequest {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Surname is required")
  private String surname;

  @Email(message = "Email must be valid")
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|email\\.ru|.*\\.edu)$",
      message = "Email must be a valid Gmail, Yahoo, email.ru, or educational domain")
  @NotBlank
  private String email;

  @NotBlank
  @Size(min = 8, message = "Password must be at least 8 characters long")
  @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).*$",
      message = "Password must contain at least one letter and one digit")
  private String password;

  @NotBlank(message = "Password is required")
  private String confirmPassword;
}


