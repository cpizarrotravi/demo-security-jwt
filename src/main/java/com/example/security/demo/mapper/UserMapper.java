package com.example.security.demo.mapper;

import com.example.security.demo.dto.UserDto;
import com.example.security.demo.entity.UserApp;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface UserMapper {

  UserApp toEntity(UserDto dto);

  @Mapping(target = "password", source = "password", ignore = true)
  UserDto toDto(UserApp user);

  @Mapping(target = "id", source = "id", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateUser(@MappingTarget UserApp user, UserDto dto);

}
