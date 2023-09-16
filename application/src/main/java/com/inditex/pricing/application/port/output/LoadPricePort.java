package com.inditex.pricing.application.port.output;

import com.inditex.pricing.domain.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface LoadPricePort {
  Optional<Price> getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
