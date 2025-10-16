package com.tramed.backend.applicationcore.systemcore.service.user;

import com.tramed.backend.core.base.model.user.User;
import com.tramed.backend.infrastructure.mybatis.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }
}
