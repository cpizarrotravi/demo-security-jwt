package com.example.security.demo.controller;

import com.example.security.demo.dto.LoginRequest;
import com.example.security.demo.dto.LoginResponse;
import com.example.security.demo.dto.SignUpRequest;
import com.example.security.demo.dto.UserDto;
import com.example.security.demo.security.BearerTokenProvider;
import com.example.security.demo.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "auth", description = "the auth api")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private BearerTokenProvider bearerTokenProvider;
  @Autowired
  private UserService userService;

  @PostMapping("/signin")
  public LoginResponse signin(@RequestBody @Valid LoginRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = bearerTokenProvider.generateToken(authentication);
    return LoginResponse.builder().token(token).build();
  }

  @PostMapping("/signup")
  public void registerUser(@RequestBody @Valid SignUpRequest request) {
    UserDto dto = UserDto.builder()
        .username(request.getUsername())
        .email(request.getEmail())
        .password(request.getPassword())
        .build();
    userService.createUser(dto);
  }

}
