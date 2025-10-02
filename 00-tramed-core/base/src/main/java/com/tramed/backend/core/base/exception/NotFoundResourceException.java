package com.tramed.backend.core.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Custom exception thrown when a requested resource is not found in the database. */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundResourceException extends RuntimeException {

  /**
   * Constructor for NotFoundResourceException with a specific message.
   *
   * @param message The detail message for the exception.
   */
  public NotFoundResourceException(String message) {
    super(message);
  }
}
