package com.fuji.ecom.shared.authentication.application;

public class AuthenticationException extends RuntimeException{
  private String message;

  public AuthenticationException() {}
  public AuthenticationException(String message) {
    super(message);
  }
}
