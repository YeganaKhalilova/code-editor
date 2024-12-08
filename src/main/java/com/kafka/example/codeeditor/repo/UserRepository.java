package com.kafka.example.codeeditor.repo;

import com.kafka.example.codeeditor.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);
}
