package com.tramed.backend.infrastructure.mybatis.repository;

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
}
