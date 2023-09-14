package com.inditex.pricing.adaptor.rest.mapper;

import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceResponseMapper {
  PriceResponse map(GetCorrectPriceQuery.PriceResult priceResult);
}
