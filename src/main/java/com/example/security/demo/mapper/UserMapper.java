package com.example.security.demo.mapper;

import com.example.security.demo.dto.UserDto;
import com.example.security.demo.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {

  User toEntity(UserDto dto);

  @Mapping(target = "password", source = "password", ignore = true)
  UserDto toDto(User user);

  @Mapping(target = "id", source = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUser(@MappingTarget User user, UserDto dto);

}
