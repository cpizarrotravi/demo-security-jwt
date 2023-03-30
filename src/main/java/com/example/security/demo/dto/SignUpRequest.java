package com.example.security.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

  @NotBlank
  @Size(min = 3, max = 15)
  @Schema(description = "username", example = "user123")
  private String username;

  @NotBlank
  @Size(max = 40)
  @Email
  @Schema(description = "email", example = "user123@gmail.com")
  private String email;

  @NotBlank
  @Size(min = 6, max = 20)
  @Schema(description = "password", example = "q23wer45ty67u8i9o")
  private String password;

}
