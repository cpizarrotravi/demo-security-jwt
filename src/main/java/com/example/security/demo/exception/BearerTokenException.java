package com.example.security.demo.exception;

public class BearerTokenException extends RuntimeException {

  public BearerTokenException(String message) {
    super(message);
  }

}
