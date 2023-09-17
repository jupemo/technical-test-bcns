package com.inditex.pricing.application.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
  private final String code = "B0003";
  private static final String MESSAGE = "Price for product id '%d' and brand id '%d' not found.";

  public NotFoundException(Integer productId, Integer brandId) {
    super(String.format(MESSAGE, productId, brandId));
  }
}
