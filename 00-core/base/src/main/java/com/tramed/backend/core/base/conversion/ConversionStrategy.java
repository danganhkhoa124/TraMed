package com.tramed.backend.core.base.conversion;

/**
 * An interface that defines the logic for converting entities.
 *
 * @param <S> Type of object to convert
 * @param <T> Type of object to convert
 */
public interface ConversionStrategy<S, T> {
  /**
   * Returns the class of the object to be converted.
   *
   * @return The class of the object to be converted
   */
  Class<S> getSourceClass();

  /**
   * Returns the class of the converted object.
   *
   * @return The converted class
   */
  Class<T> getTargetClass();

  /**
   * Converts the specified object.
   *
   * @param source The object to convert
   * @return The converted object
   */
  T convert(S source);
}
