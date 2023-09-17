package com.inditex.pricing.adaptor.rest.controller;

import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.adaptor.rest.mapper.PriceResponseMapper;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery.Command;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PricingController {

  private final PriceResponseMapper priceResponseMapper;
  private final GetCorrectPriceQuery getCorrectPriceQuery;

  @GetMapping("prices/products/{productId}/brands/{brandId}")
  public PriceResponse getPrice(
      @PathVariable Integer productId,
      @PathVariable Integer brandId,
      @RequestParam(required = false) LocalDateTime date) {

    return priceResponseMapper.map(
        getCorrectPriceQuery.retrievePrice(
            new Command(date != null ? date : LocalDateTime.now(), productId, brandId)));
  }
}
