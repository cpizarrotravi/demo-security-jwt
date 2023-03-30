package com.example.security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

  @Schema(description = "token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaXNz.driTD38psOuJuEByFrfr")
  private String token;

}
