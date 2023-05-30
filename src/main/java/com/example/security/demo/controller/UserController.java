package com.example.security.demo.controller;

import com.example.security.demo.dto.UserDto;
import com.example.security.demo.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "user", description = "the user api")
@SecurityRequirement(name = "JWT")
public interface UserController {

  @Operation(summary = "Get user", description = "Get user information", tags = {"user"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation", content = {
          @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
      })
  })
  UserDetails getUser(Authentication authentication);

  @Operation(summary = "Patch user", description = "Update user information", tags = {"user"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation"),
      @ApiResponse(responseCode = "400", description = "Bad Request")
  })
  void patchUser(@Parameter(description = "Id of user") @PathVariable Long id, @RequestBody @Valid UserDto dto, Authentication authentication) throws UserNotFoundException;

  @Operation(summary = "Delete user", description = "Delete user", tags = {"user"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation")
  })
  void deleteUser(@Parameter(description = "Id of user") @PathVariable Long id, Authentication authentication) throws UserNotFoundException;

}
