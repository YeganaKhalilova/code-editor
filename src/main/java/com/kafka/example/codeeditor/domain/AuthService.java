package com.kafka.example.codeeditor.domain;


import com.kafka.example.codeeditor.cache.RedisJWTTokenService;
import com.kafka.example.codeeditor.config.JwtUtil;
import com.kafka.example.codeeditor.domain.dto.AuthResponse;
import com.kafka.example.codeeditor.domain.dto.LoginRequest;
import com.kafka.example.codeeditor.domain.dto.LogoutRequest;
import com.kafka.example.codeeditor.domain.dto.RegisterRequest;
import com.kafka.example.codeeditor.mapper.AuthMapper;
import com.kafka.example.codeeditor.rest.exception.ApplicationException;
import com.kafka.example.codeeditor.rest.exception.enums.Exceptions;
import com.kafka.example.codeeditor.domain.model.User;
import com.kafka.example.codeeditor.repo.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final AuthMapper mapper;
  private final AuthenticationManager authenticationManager;
  private final RedisJWTTokenService redisJWTTokenService;

  public AuthResponse register(RegisterRequest request) {
    if (!request.getConfirmPassword().equals(request.getPassword())) {
      throw new ApplicationException(Exceptions.PASSWORD_MISMATCH_EXCEPTION);
    }
    Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
    if (existingUser.isPresent()) {
      throw new ApplicationException(Exceptions.USER_ALREADY_EXIST);
    }

    User user = mapper.mapToUserEntity(request, passwordEncoder);
    user.setRole(User.Role.USER);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
    String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthResponse login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));
    User user =
        userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ApplicationException(Exceptions.USER_NOT_FOUND));
    String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
    String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
}

  public void logout(LogoutRequest request) {
    var token = request.getRefreshToken();
    String email = request.getEmail();
    if (redisJWTTokenService.validateRefreshToken(email, token)) {
      redisJWTTokenService.deleteRefreshToken(email);
    } else {
      throw new ApplicationException(Exceptions.INVALID_TOKEN_EXCEPTION);
    }
  }
}
