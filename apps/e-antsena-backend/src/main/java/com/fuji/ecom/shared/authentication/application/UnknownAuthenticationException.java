package com.fuji.ecom.shared.authentication.application;

public class UnknownAuthenticationException extends RuntimeException{
  private String message;

  public UnknownAuthenticationException() {}
  public UnknownAuthenticationException(String message) {
    super(message);
  }
}
