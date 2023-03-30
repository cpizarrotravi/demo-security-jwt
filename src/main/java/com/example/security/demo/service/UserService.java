package com.example.security.demo.service;

import com.example.security.demo.dto.UserDto;

public interface UserService {

  void createUser(UserDto dto);

  void updateUser(Long id, UserDto dto);

  void deleteUser(Long id);

}
