package com.inditex.pricing.domain;

import com.inditex.pricing.domain.exception.CurrencyException;
import com.inditex.pricing.domain.exception.DateException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record Price(
    Integer priceList,
    Integer productId,
    Integer brandId,
    String currency,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Integer priority,
    BigDecimal price) {

  public Price(
      Integer priceList,
      Integer productId,
      Integer brandId,
      String currency,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Integer priority,
      BigDecimal price) {

    checkDates(startDate, endDate);

    this.priceList = priceList;
    this.productId = productId;
    this.brandId = brandId;
    this.currency = checkCurrency(currency);
    this.startDate = startDate;
    this.endDate = endDate;
    this.priority = priority;
    this.price = price;
  }

  private void checkDates(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate.isAfter(endDate)) throw new DateException(startDate, endDate);
  }

  private String checkCurrency(String currency) {
    for (Currency availableCurrencies : Currency.getAvailableCurrencies()) {
      if (availableCurrencies.getCurrencyCode().equals(currency)) return currency;
    }
    throw new CurrencyException(currency);
  }
}
