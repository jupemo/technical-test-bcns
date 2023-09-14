package com.inditex.pricing.application.port.input.mapper;

import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import com.inditex.pricing.domain.Price;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper {
  GetCorrectPriceQuery.PriceResult map(Price price);
}
