package com.inditex.pricing.adaptor.database.repository;

import com.inditex.pricing.application.port.output.LoadPricePort;
import com.inditex.pricing.domain.Price;
import java.time.LocalDateTime;

public class PriceRepository implements LoadPricePort {

  @Override
  public Price getPrice(LocalDateTime date, Integer productId, Integer brandId) {
    return null;
  }
}
