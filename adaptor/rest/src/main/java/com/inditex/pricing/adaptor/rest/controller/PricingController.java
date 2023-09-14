package com.inditex.pricing.adaptor.rest.controller;

import com.inditex.pricing.adaptor.rest.dto.PriceRequest;
import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.adaptor.rest.mapper.PriceRequestMapper;
import com.inditex.pricing.adaptor.rest.mapper.PriceResponseMapper;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PricingController {

  private final PriceRequestMapper priceRequestMapper;
  private final PriceResponseMapper priceResponseMapper;
  private final GetCorrectPriceQuery getCorrectPriceQuery;

  @GetMapping("price/{productId}")
  public PriceResponse getPrice(
      @PathVariable Integer productId, @Valid @RequestBody PriceRequest priceRequest) {
    return priceResponseMapper.map(
        getCorrectPriceQuery.retrievePrice(priceRequestMapper.map(productId, priceRequest)));
  }
}
