package com.tramed.backend.infrastructure.mybatis.repository;

import com.tramed.backend.core.base.model.common.UserId;
import com.tramed.backend.core.base.model.user.User;
import com.tramed.backend.infrastructure.mybatis.entity.user.UserEntity;
import com.tramed.backend.infrastructure.mybatis.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final UserMapper userMapper;

  public Optional<User> findByUsername(String username) {
    UserEntity entity = userMapper.findByUsername(username);
    return Optional.ofNullable(entity).map(UserEntity::toDomain);
  }

  public Optional<User> findById(UserId userId) {
    UserEntity entity = userMapper.findById(userId.value());
    return Optional.ofNullable(entity).map(UserEntity::toDomain);
  }

  public void insert(User user) {
    userMapper.insert(UserEntity.fromDomain(user));
  }

  public void updateStatus(UserId userId, boolean active) {
    userMapper.updateStatus(userId.value(), active);
  }
}
