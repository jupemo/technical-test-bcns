package com.inditex.pricing.adaptor.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record PriceRequest(
    @JsonProperty("application-date") LocalDateTime applicationDate,
    @NotNull @JsonProperty("price-id") Integer priceId) {}
