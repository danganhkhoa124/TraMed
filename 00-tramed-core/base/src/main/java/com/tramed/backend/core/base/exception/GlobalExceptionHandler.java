package com.tramed.backend.core.base.exception;

import static com.tramed.backend.core.utils.common.CommonConstant.EXCEPTION;

import com.tramed.backend.core.base.model.common.ApiResponse;
import com.tramed.backend.core.base.model.common.MultiRecordErrorResponse;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private final MessageSource messageSource;

  public GlobalExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(PreconditionException.class)
  public ApiResponse handleValidationExceptions(PreconditionException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());

    String message = getMessageValue(ex.getMessage(), (Object) null);
    response.addFirstRecordDetail(null, ex.getMessage(), message);

    return response;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());

    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            error -> {
              String message = getMessageValue(error.getDefaultMessage(), (Object) null);
              response.addFirstRecordDetail(error.getField(), error.getDefaultMessage(), message);
            });

    return response;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundResourceException.class)
  public ApiResponse handleValidationExceptions(NotFoundResourceException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());

    String message = getMessageValue(ex.getMessage(), (Object) null);
    response.addFirstRecordDetail(null, ex.getMessage(), message);

    return response;
  }

  @ExceptionHandler(InvalidArgumentModelException.class)
  @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
  public final ApiResponse handleValidationExceptions(InvalidArgumentModelException ex) {
    final MultiRecordErrorResponse errorResponse =
        new MultiRecordErrorResponse(HttpStatus.PRECONDITION_FAILED.value(), ex.getMessage());
    errorResponse.addFirstRecordDetail(EXCEPTION, ex.getMessage());
    return errorResponse;
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ApiResponse handleValidationExceptions(IllegalArgumentException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());

    response.addFirstRecordDetail(EXCEPTION, ex.getLocalizedMessage());

    return response;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  public ApiResponse handleUnauthorizedException(UnauthorizedException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());

    String message = getMessageValue(ex.getMessage(), (Object) null);
    response.addFirstRecordDetail(EXCEPTION, message != null ? message : ex.getMessage());

    return response;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(NullPointerException.class)
  public ApiResponse handleNullPointerException(NullPointerException ex) {
    final MultiRecordErrorResponse response =
        new MultiRecordErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), "A null pointer exception occurred");

    response.addFirstRecordDetail(EXCEPTION, ex.getLocalizedMessage());

    return response;
  }

  /**
   * Get message value by error code
   *
   * @param code The error code
   * @param args The arguments
   * @return The message value
   */
  private String getMessageValue(String code, @Nullable Object... args) {
    try {
      return messageSource.getMessage(code, args, Locale.ENGLISH);
    } catch (Exception e) {
      return null;
    }
  }
}
