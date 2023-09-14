package com.inditex.pricing.adaptor.rest.mapper;

import com.inditex.pricing.adaptor.rest.dto.PriceRequest;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PriceRequestMapper {
  @Mapping(source = "productId", target = "productId")
  GetCorrectPriceQuery.Command map(Integer productId, PriceRequest priceRequest);
}
