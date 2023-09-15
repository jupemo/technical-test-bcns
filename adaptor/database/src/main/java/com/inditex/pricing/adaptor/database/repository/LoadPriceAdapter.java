package com.inditex.pricing.adaptor.database.repository;

import com.inditex.pricing.adaptor.database.repository.jpa.SpringDataPriceRepository;
import com.inditex.pricing.application.port.output.LoadPricePort;
import com.inditex.pricing.domain.Price;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoadPriceAdapter implements LoadPricePort {

  private final SpringDataPriceRepository priceEntityRepository;

  @Override
  public Price getPrice(LocalDateTime date, Integer productId, Integer brandId) {
     var entity = priceEntityRepository.findByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfter(
        productId, brandId, date, date);
     return null;
  }
}
