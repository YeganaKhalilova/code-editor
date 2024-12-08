package com.kafka.example.codeeditor.rest;


import com.kafka.example.codeeditor.domain.AuthService;
import com.kafka.example.codeeditor.domain.dto.AuthResponse;
import com.kafka.example.codeeditor.domain.dto.LoginRequest;
import com.kafka.example.codeeditor.domain.dto.LogoutRequest;
import com.kafka.example.codeeditor.domain.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

 private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
    return ResponseEntity.ok(authService.register(request));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login (@Valid @RequestBody LoginRequest request){
    return ResponseEntity.ok(authService.login(request));
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
    authService.logout(request);
    return ResponseEntity.ok().build();
  }

}
