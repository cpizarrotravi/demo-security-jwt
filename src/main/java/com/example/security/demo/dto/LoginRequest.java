package com.example.security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

  @NotBlank
  @Schema(description = "username", example = "user123", requiredMode = Schema.RequiredMode.REQUIRED)
  private String username;
  @NotBlank
  @Schema(description = "password", example = "q23wer45ty67u8i9o", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

}
