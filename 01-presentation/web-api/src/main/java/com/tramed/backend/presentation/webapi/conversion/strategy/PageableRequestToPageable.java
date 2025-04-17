package com.tramed.backend.presentation.webapi.conversion.strategy;

import com.tramed.backend.core.base.conversion.ConversionStrategy;
import com.tramed.backend.core.base.pagination.PageRequest;
import com.tramed.backend.presentation.webapi.model.common.PageableRequest;
import java.util.Collections;
import java.util.Objects;

public class PageableRequestToPageable implements ConversionStrategy<PageableRequest, PageRequest> {

  /**
   * Returns the class of the object to be converted.
   *
   * @return The class of the object to be converted
   */
  @Override
  public Class<PageableRequest> getSourceClass() {
    return PageableRequest.class;
  }

  /**
   * Returns the class of the converted object.
   *
   * @return The converted class
   */
  @Override
  public Class<PageRequest> getTargetClass() {
    return PageRequest.class;
  }

  /**
   * Converts the specified object.
   *
   * @param source The object to convert
   * @return The converted object
   */
  @Override
  public PageRequest convert(PageableRequest source) {
    return new PageRequest(
        Objects.nonNull(source.page()) && Integer.parseInt(source.page()) > 0
            ? Integer.parseInt(source.page())
            : 1,
        Objects.nonNull(source.limit()) && Integer.parseInt(source.limit()) > 0
            ? Integer.parseInt(source.limit())
            : 10,
        Collections.emptyList());
  }
}
