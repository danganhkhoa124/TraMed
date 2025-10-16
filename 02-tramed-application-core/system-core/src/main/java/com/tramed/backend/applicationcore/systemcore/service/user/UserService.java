package com.tramed.backend.applicationcore.systemcore.service.user;

import com.fasterxml.uuid.Generators;
import com.tramed.backend.core.base.exception.NotFoundResourceException;
import com.tramed.backend.core.base.exception.PreconditionException;
import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.user.User;
import com.tramed.backend.core.base.model.user.UserRole;
import com.tramed.backend.core.base.security.PasswordHasher;
import com.tramed.backend.infrastructure.mybatis.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordHasher passwordHasher;

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findById(UserId userId) {
    return userRepository.findById(userId);
  }

  @Transactional
  public User registerUser(RegisterUserCommand command) {
    userRepository
        .findByUsername(command.username())
        .ifPresent(
            user -> {
              throw new PreconditionException("E0006");
            });

    UserId userId = new UserId(Generators.timeBasedEpochGenerator().generate());
    User user =
        new User(
            userId,
            command.username(),
            passwordHasher.encode(command.password()),
            command.fullName(),
            false,
            UserRole.USER);

    userRepository.insert(user);
    return user;
  }

  @Transactional
  public User updateUserStatus(UserId userId, boolean active) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NotFoundResourceException("E0007"));

    User updatedUser =
        new User(
            user.userId(), user.username(), user.passwordHash(), user.fullName(), active, user.role());
    userRepository.updateStatus(userId, active);
    return updatedUser;
  }
}
