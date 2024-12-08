package com.kafka.example.codeeditor.config;

import com.kafka.example.codeeditor.domain.model.User;
import com.kafka.example.codeeditor.repo.UserRepository;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetailsService {

  private final UserRepository repository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        getAuthorities(user)
    );
  }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }
}
