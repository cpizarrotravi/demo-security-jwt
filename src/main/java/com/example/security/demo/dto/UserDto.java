package com.example.security.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

  @Schema(description = "id of user", example = "1")
  private long id;
  @Schema(description = "username", example = "user123")
  private String username;
  @Schema(description = "password", example = "q23er5t6y7u8")
  private String password;
  @Email
  @Schema(description = "email", example = "user123@gmail.com")
  private String email;
  @Schema(description = "enabled", example = "true")
  private boolean enabled;

}
