package com.fuji.ecom.shared.authentication.application;

public class NotAuthenticatedUserException extends RuntimeException{
  private String message;

  public NotAuthenticatedUserException() {}
  public NotAuthenticatedUserException(String message) {
    super(message);
  }
}
