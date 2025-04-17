package com.tramed.backend.presentation.webapi.conversion;

import com.tramed.backend.core.base.conversion.ConversionStrategy;
import com.tramed.backend.presentation.webapi.conversion.strategy.NotificationQueryResultToNotificationForQueryResponse;
import com.tramed.backend.presentation.webapi.conversion.strategy.PageableRequestToPageable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.tuple.Pair;

public class ConversionStrategies {
  /** Define STRATEGIES_MAP */
  private static final Map<Pair<Class<?>, Class<?>>, ConversionStrategy<?, ?>> STRATEGIES_MAP =
      new ConcurrentHashMap<>();

  // Add Strategy here in the following way: addStrategy(new Class())
  static {
    addStrategy(new PageableRequestToPageable());
    addStrategy(new NotificationQueryResultToNotificationForQueryResponse());
  }

  /**
   * Add strategy
   *
   * @param strategy ConversionStrategy<?, ?>
   */
  private static void addStrategy(ConversionStrategy<?, ?> strategy) {
    STRATEGIES_MAP.put(Pair.of(strategy.getSourceClass(), strategy.getTargetClass()), strategy);
  }

  /**
   * Get strategy
   *
   * @param sourceClass Class<?>
   * @param targetClass Class<?>
   * @return Optional<ConversionStrategy<?, ?>>
   */
  public static Optional<ConversionStrategy<?, ?>> getStrategy(
      Class<?> sourceClass, Class<?> targetClass) {
    return Optional.ofNullable(STRATEGIES_MAP.get(Pair.of(sourceClass, targetClass)));
  }
}
