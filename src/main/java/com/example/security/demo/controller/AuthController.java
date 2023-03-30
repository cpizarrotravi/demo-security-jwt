package com.example.security.demo.controller;

import com.example.security.demo.dto.LoginRequest;
import com.example.security.demo.dto.LoginResponse;
import com.example.security.demo.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "auth", description = "the auth api")
public interface AuthController {

  @Operation(summary = "Signin", description = "Login", tags = {"auth"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
      })
  })
  LoginResponse signin(@RequestBody @Valid LoginRequest request);

  @Operation(summary = "Signup", description = "Register", tags = {"auth"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation")
  })
  void signup(@RequestBody @Valid SignUpRequest request);

}
