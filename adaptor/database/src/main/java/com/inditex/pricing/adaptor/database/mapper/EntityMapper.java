package com.inditex.pricing.adaptor.database.mapper;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import com.inditex.pricing.domain.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {
  Price map(PriceEntity entity);
}
