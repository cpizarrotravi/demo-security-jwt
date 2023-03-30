package com.example.security.demo.service.impl;

import com.example.security.demo.dto.UserDto;
import com.example.security.demo.entity.User;
import com.example.security.demo.exception.UserNotFoundException;
import com.example.security.demo.mapper.UserMapper;
import com.example.security.demo.repository.UserRepository;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public void createUser(UserDto dto) {
    User user = userMapper.toEntity(dto);
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void updateUser(Long id, UserDto dto) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.format("User not found with id:%s", id)));
    userMapper.updateUser(user, dto);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(String.format("User not found with id:%s", id)));
    user.setEnabled(false);
    userRepository.save(user);
  }

}
