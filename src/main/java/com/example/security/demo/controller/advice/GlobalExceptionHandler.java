package com.example.security.demo.controller.advice;

import com.example.security.demo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(error -> String.format("%s %s", error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());
    return ResponseEntity.badRequest().body(getErrorsMap(errors));
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Map<String, List<String>>> handleNotFoundException(UserNotFoundException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getErrorsMap(errors));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return ResponseEntity.internalServerError().body(getErrorsMap(errors));
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return ResponseEntity.internalServerError().body(getErrorsMap(errors));
  }

  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();
    errorResponse.put("errors", errors);
    return errorResponse;
  }

}
