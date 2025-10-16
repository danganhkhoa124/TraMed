package com.tramed.backend.core.base.exception;

/** Exception thrown when a request lacks valid authentication credentials. */
public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);
  }
}
