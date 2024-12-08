package com.kafka.example.codeeditor.domain;


import com.kafka.example.codeeditor.config.JwtUtil;
import com.kafka.example.codeeditor.dto.AuthResponse;
import com.kafka.example.codeeditor.dto.RegisterRequest;
import com.kafka.example.codeeditor.mapper.UserMapper;
import com.kafka.example.codeeditor.model.User;
import com.kafka.example.codeeditor.repo.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtConfig;
  private final UserMapper mapper;


  public AuthResponse register(RegisterRequest request) {

    if (!request.getConfirmPassword().equals(request.getPassword())) {
      throw new RuntimeException();  //replace with custom exception
    }


    Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
    if (existingUser.isPresent()) {
      throw new RuntimeException();  //replace with custom exception
    }

    User user = mapper.mapToUserEntity(request, passwordEncoder);
    user.setRole(User.Role.USER);
    user.setCreatedAt(LocalDateTime.now());
    user.setUpdatedAt(LocalDateTime.now());
    userRepository.save(user);

    String accessToken = jwtConfig.generateAccessToken(user.getEmail(), user.getRole().name());
    String refreshToken = jwtConfig.generateRefreshToken(user.getEmail());

    return AuthResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
}
}
