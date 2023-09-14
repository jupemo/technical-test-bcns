package com.inditex.pricing.application.port.output;

import com.inditex.pricing.domain.Price;
import java.time.LocalDateTime;

public interface LoadPricePort {
  Price getPrice(LocalDateTime date, Integer productId, Integer brandId);
}
