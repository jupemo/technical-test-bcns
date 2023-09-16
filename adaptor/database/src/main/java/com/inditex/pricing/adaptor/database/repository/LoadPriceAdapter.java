package com.inditex.pricing.adaptor.database.repository;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import com.inditex.pricing.adaptor.database.mapper.EntityMapper;
import com.inditex.pricing.adaptor.database.repository.jpa.SpringDataPriceRepository;
import com.inditex.pricing.adaptor.database.specification.PriceSpecification;
import com.inditex.pricing.application.port.output.LoadPricePort;
import com.inditex.pricing.domain.Price;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoadPriceAdapter implements LoadPricePort {

  private final SpringDataPriceRepository priceEntityRepository;
  private final PriceSpecification priceSpecification;
  private final EntityMapper entityMapper;

  @Override
  public Optional<Price> getPrice(LocalDateTime date, Integer productId, Integer brandId) {

    var specification =
        priceSpecification
            .equal("productId", productId)
            .and(priceSpecification.equal("brandId", brandId))
            .and(priceSpecification.betweenDates(date));

    return priceEntityRepository.findAll(specification).stream()
        .max(Comparator.comparingInt(PriceEntity::getPriority))
        .map(entityMapper::map);
  }
}
