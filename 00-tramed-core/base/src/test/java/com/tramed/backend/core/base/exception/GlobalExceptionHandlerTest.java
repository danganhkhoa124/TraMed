package com.tramed.backend.core.base.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.tramed.backend.core.base.model.common.MultiRecordErrorResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class GlobalExceptionHandlerTest {
  @Test
  void handleValidationExceptions_preconditionExceptionShouldReturnBadRequest() {
    MessageSource messageSource = mock(MessageSource.class);
    when(messageSource.getMessage(
            eq("precondition.failed"), any(Object[].class), eq(Locale.ENGLISH)))
        .thenReturn("Precondition failed");

    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse)
            handler.handleValidationExceptions(new PreconditionException("precondition.failed"));

    assertThat(response.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(response.getDetail()).containsKey("exception");

    @SuppressWarnings("unchecked")
    List<Map<String, String>> details =
        (List<Map<String, String>>) response.getDetail().get("exception");
    assertThat(details)
        .singleElement()
        .satisfies(
            detail -> {
              assertThat(detail).doesNotContainKey("field");
              assertThat(detail.get("errorCode")).isEqualTo("precondition.failed");
              assertThat(detail.get("message")).isEqualTo("Precondition failed");
            });

    verify(messageSource)
        .getMessage(eq("precondition.failed"), any(Object[].class), eq(Locale.ENGLISH));
  }

  @Test
  void handleValidationExceptions_methodArgumentNotValidShouldReturnFieldErrors()
      throws NoSuchMethodException {
    MessageSource messageSource = mock(MessageSource.class);
    when(messageSource.getMessage(
            eq("first.name.required"), any(Object[].class), eq(Locale.ENGLISH)))
        .thenReturn("First name is required");
    when(messageSource.getMessage(eq("age.invalid"), any(Object[].class), eq(Locale.ENGLISH)))
        .thenReturn("Age is invalid");

    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "sample");
    bindingResult.addError(new FieldError("sample", "firstName", "first.name.required"));
    bindingResult.addError(new FieldError("sample", "age", "age.invalid"));

    Method sampleMethod = Sample.class.getDeclaredMethod("method", String.class);
    MethodParameter methodParameter = new MethodParameter(sampleMethod, 0);

    MethodArgumentNotValidException exception =
        new MethodArgumentNotValidException(methodParameter, bindingResult);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse) handler.handleValidationExceptions(exception);

    assertThat(response.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(response.getDetail()).containsKey("exception");

    @SuppressWarnings("unchecked")
    List<Map<String, String>> details =
        (List<Map<String, String>>) response.getDetail().get("exception");
    assertThat(details)
        .hasSize(2)
        .anySatisfy(
            detail -> {
              assertThat(detail.get("field")).isEqualTo("firstName");
              assertThat(detail.get("errorCode")).isEqualTo("first.name.required");
              assertThat(detail.get("message")).isEqualTo("First name is required");
            })
        .anySatisfy(
            detail -> {
              assertThat(detail.get("field")).isEqualTo("age");
              assertThat(detail.get("errorCode")).isEqualTo("age.invalid");
              assertThat(detail.get("message")).isEqualTo("Age is invalid");
            });
  }

  @Test
  void handleValidationExceptions_shouldReturnNotFoundResponse() {
    MessageSource messageSource = mock(MessageSource.class);
    when(messageSource.getMessage(
            eq("resource.not.found"), any(Object[].class), eq(Locale.ENGLISH)))
        .thenReturn("Resource not found");

    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse)
            handler.handleValidationExceptions(new NotFoundResourceException("resource.not.found"));

    assertThat(response.getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getMessage()).isEqualTo(HttpStatus.NOT_FOUND.getReasonPhrase());
    assertThat(response.getDetail()).isNotNull().containsKey("exception");

    @SuppressWarnings("unchecked")
    List<Map<String, String>> details =
        (List<Map<String, String>>) response.getDetail().get("exception");
    assertThat(details)
        .singleElement()
        .satisfies(
            detail -> {
              assertThat(detail.get("errorCode")).isEqualTo("resource.not.found");
              assertThat(detail.get("message")).isEqualTo("Resource not found");
            });
  }

  @Test
  void handleValidationExceptions_invalidArgumentModelShouldReturnPreconditionFailed() {
    MessageSource messageSource = mock(MessageSource.class);
    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse)
            handler.handleValidationExceptions(new InvalidArgumentModelException("invalid model"));

    assertThat(response.getCode()).isEqualTo(HttpStatus.PRECONDITION_FAILED.value());
    assertThat(response.getMessage()).isEqualTo("invalid model");
    assertThat(response.getDetail()).containsEntry("exception", List.of("invalid model"));
  }

  @Test
  void handleValidationExceptions_illegalArgumentShouldReturnBadRequest() {
    MessageSource messageSource = mock(MessageSource.class);
    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse)
            handler.handleValidationExceptions(new IllegalArgumentException("illegal argument"));

    assertThat(response.getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getMessage()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
    assertThat(response.getDetail()).containsEntry("exception", List.of("illegal argument"));
  }

  @Test
  void handleNullPointerException_shouldReturnInternalServerError() {
    MessageSource messageSource = mock(MessageSource.class);
    GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);

    MultiRecordErrorResponse response =
        (MultiRecordErrorResponse)
            handler.handleNullPointerException(new NullPointerException("npe happened"));

    assertThat(response.getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    assertThat(response.getMessage()).isEqualTo("A null pointer exception occurred");
    assertThat(response.getDetail()).containsEntry("exception", List.of("npe happened"));
  }

  private static final class Sample {
    @SuppressWarnings("unused")
    private void method(String argument) {}
  }
}
