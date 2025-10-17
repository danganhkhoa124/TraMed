package com.tramed.backend.infrastructure.mybatis.mapper;

import com.tramed.backend.infrastructure.mybatis.entity.user.UserEntity;
import java.util.UUID;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  UserEntity findByUsername(@Param("username") String username);

  UserEntity findById(@Param("userId") UUID userId);

  void insert(@Param("user") UserEntity user);

  void updateStatus(@Param("userId") UUID userId, @Param("active") boolean active);
}
