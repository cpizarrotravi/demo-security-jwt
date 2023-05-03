package com.example.security.demo.repository;

import com.example.security.demo.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long>, JpaSpecificationExecutor<UserApp> {

  Optional<UserApp> findByUsernameOrEmail(String username, String email);

}
