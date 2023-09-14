package com.inditex.pricing.application.port.input.interaptor;

import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import com.inditex.pricing.application.port.input.mapper.PriceMapper;
import com.inditex.pricing.application.port.output.LoadPricePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCorrectPriceQueryService implements GetCorrectPriceQuery {

  private final LoadPricePort loadPricePort;
  private final PriceMapper priceMapper;

  @Override
  public PriceResult retrievePrice(Command command) {
    return priceMapper.map(
        loadPricePort.getPrice(command.date(), command.productId(), command.brandId()));
  }
}
