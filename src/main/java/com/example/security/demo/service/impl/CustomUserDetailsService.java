package com.example.security.demo.service.impl;

import com.example.security.demo.entity.UserApp;
import com.example.security.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserApp user = userRepository.findByUsernameOrEmail(username, username)
        .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username or email: %s", username)));
    return getUserDetails(user);
  }

  private UserDetails getUserDetails(UserApp user) {
    return User.builder()
        .username(user.getUsername())
        .password(user.getPassword())
        .disabled(!user.isEnabled())
        .authorities(AuthorityUtils.NO_AUTHORITIES)
        .build();
  }

}
