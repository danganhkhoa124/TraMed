package com.tramed.backend.infrastructure.mybatis.mapper;

import com.tramed.backend.infrastructure.mybatis.entity.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

  UserEntity findByUsername(@Param("username") String username);
}
