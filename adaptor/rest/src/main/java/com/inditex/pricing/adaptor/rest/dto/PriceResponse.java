package com.inditex.pricing.adaptor.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PriceResponse(
    @JsonProperty("product-id") Integer productId,
    @JsonProperty("start-date") LocalDateTime startDate,
    @JsonProperty("end-date") LocalDateTime endDate,
    @JsonProperty("price-list") Integer priceList,
    @JsonProperty("brand-id") Integer brandId,
    @JsonProperty("price") BigDecimal price) {}
