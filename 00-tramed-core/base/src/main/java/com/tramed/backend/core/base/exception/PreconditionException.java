package com.tramed.backend.core.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Custom exception thrown when a requested error. */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PreconditionException extends RuntimeException {

  /**
   * Constructor for PreconditionException with a specific message.
   *
   * @param message The detail message for the exception.
   */
  public PreconditionException(String message) {
    super(message);
  }
}
