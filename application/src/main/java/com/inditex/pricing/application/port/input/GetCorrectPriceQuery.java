package com.inditex.pricing.application.port.input;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface GetCorrectPriceQuery {

  PriceResult retrievePrice(Command command);

  record Command(
      @NotNull LocalDateTime date,
      @NotNull Integer productId,
      @NotNull Integer brandId) {}

  record PriceResult(
      Integer productId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Integer priceList,
      Integer brandId,
      BigDecimal price

  ) {}
}
