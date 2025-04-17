package com.tramed.backend.presentation.webapi.conversion;

import com.tramed.backend.core.base.conversion.ConversionStrategy;

public class Converter {
  /**
   * Converts the specified input object to an object of the specified class.
   *
   * @param <S> Type of object to convert from
   * @param <T> Type of object to convert to
   * @param source Object to convert from
   * @param targetClass Destination class
   * @return Destination object
   * @throws IllegalArgumentException if no suitable conversion strategy can be found
   */
  @SuppressWarnings("unchecked")
  public static <S, T> T convert(S source, Class<T> targetClass) {
    return ConversionStrategies.getStrategy(source.getClass(), targetClass)
        .map(strategy -> ((ConversionStrategy<S, T>) strategy).convert(source))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Convert failed from %s to %s", source.getClass(), targetClass)));
  }
}
