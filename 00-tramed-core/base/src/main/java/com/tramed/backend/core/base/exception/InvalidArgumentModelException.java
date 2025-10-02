package com.tramed.backend.core.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Custom exception thrown when a resource was not invalid. */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidArgumentModelException extends RuntimeException {

  /**
   * Constructor for NotFoundResourceException with a specific message.
   *
   * @param message The detail message for the exception.
   */
  public InvalidArgumentModelException(String message) {
    super(message);
  }
}
