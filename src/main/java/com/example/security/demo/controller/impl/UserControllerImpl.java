package com.example.security.demo.controller.impl;

import com.example.security.demo.controller.UserController;
import com.example.security.demo.dto.UserDto;
import com.example.security.demo.entity.UserApp;
import com.example.security.demo.exception.UserNotFoundException;
import com.example.security.demo.mapper.UserMapper;
import com.example.security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private UserMapper userMapper;

  @Override
  @GetMapping
  public UserDto getUser(Authentication authentication) {
    return userMapper.toDto((UserApp) authentication.getPrincipal());
  }

  @Override
  @PatchMapping("/{id}")
  @PreAuthorize("#id==#authentication.principal.id")
  public void patchUser(@PathVariable Long id, @RequestBody @Valid UserDto dto, Authentication authentication) throws UserNotFoundException {
    userService.updateUser(id, dto);
  }

  @Override
  @DeleteMapping("/{id}")
  @PreAuthorize("#id==#authentication.principal.id")
  public void deleteUser(@PathVariable Long id, Authentication authentication) throws UserNotFoundException {
    userService.deleteUser(id);
  }

}
