package com.tramed.backend.core.base.model.common;

import static com.tramed.backend.core.utils.common.CommonConstant.EXCEPTION;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonTypeName("error")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"code", "message", "rowCount", "detail"})
public class MultiRecordErrorResponse implements ApiResponse {

  private static final String KEY_CODE = "code";
  private static final String KEY_DETAIL = "detail";
  private static final String KEY_MESSAGE = "message";
  private static final String KEY_FIELD = "field";
  private static final String KEY_ERROR_CODE = "errorCode";

  @Positive @JsonProperty(KEY_CODE)
  private int code;

  @NotBlank
  @JsonProperty(KEY_MESSAGE)
  private String message;

  @JsonProperty(KEY_DETAIL)
  @Setter(AccessLevel.NONE)
  private Map<String, Object> detail = new ConcurrentHashMap<>();

  @JsonIgnore private int rowCount;

  /**
   * @param code
   * @param message
   */
  public MultiRecordErrorResponse(@Positive int code, @NotBlank String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Add record-level detail for first record only. Use for single-record requests.
   *
   * @param field
   * @param errorCode
   * @param value
   */
  public void addFirstRecordDetail(
      @NotBlank String field, @NotBlank String errorCode, @NotBlank String value) {
    addRecordDetail(field, errorCode, value);
  }

  /**
   * Add record-level detail for first record only. Use for single-record requests.
   *
   * @param field
   * @param value
   */
  public void addFirstRecordDetail(@NotBlank String field, @NotBlank String value) {
    addRecordDetail(field, value);
  }

  /**
   * Add individual record-level detail. Use for multi-record requests with error code.
   *
   * @param field
   * @param errorCode
   * @param value
   */
  @SuppressWarnings("unchecked")
  public void addRecordDetail(
      @NotBlank String field, @NotBlank String errorCode, @NotBlank String value) {
    List<Map<String, String>> valueList;

    if (detail.containsKey(EXCEPTION)) {
      valueList = (List<Map<String, String>>) detail.get(EXCEPTION);
    } else {
      valueList = new ArrayList<>();
    }

    Map<String, String> errorDetail = new ConcurrentHashMap<>();
    if (field != null) {
      errorDetail.put(KEY_FIELD, field);
    }
    errorDetail.put(KEY_ERROR_CODE, errorCode);
    errorDetail.put(KEY_MESSAGE, value != null ? value : errorCode);

    valueList.add(errorDetail);
    detail.put(EXCEPTION, valueList);

    rowCount += 1;
  }

  /**
   * Add individual record-level detail. Use for multi-record requests.
   *
   * @param field
   * @param value
   */
  @SuppressWarnings("unchecked")
  public void addRecordDetail(@NotBlank String field, @NotBlank String value) {
    List<String> valueList;

    if (detail.containsKey(field)) {
      valueList = (List<String>) detail.get(field);
    } else {
      valueList = new ArrayList<>();
    }

    valueList.add(value);
    detail.put(field, valueList);

    rowCount += 1;
  }
}
