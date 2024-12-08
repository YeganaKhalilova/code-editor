package com.kafka.example.codeeditor.mapper;

import com.kafka.example.codeeditor.domain.dto.RegisterRequest;
import com.kafka.example.codeeditor.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;


@Mapper(componentModel = "spring")
public interface AuthMapper {

  @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "enabled", constant = "false")
  User mapToUserEntity(RegisterRequest request, PasswordEncoder passwordEncoder);

  RegisterRequest mapToUserRequest(User user);
}

