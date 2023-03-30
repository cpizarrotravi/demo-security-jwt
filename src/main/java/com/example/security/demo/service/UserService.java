package com.example.security.demo.service;

import com.example.security.demo.dto.UserDto;
import com.example.security.demo.exception.UserNotFoundException;

public interface UserService {

  void createUser(UserDto dto);

  void updateUser(Long id, UserDto dto) throws UserNotFoundException;

  void deleteUser(Long id) throws UserNotFoundException;

}
