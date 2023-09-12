package com.inditex.pricing.domain;

import com.inditex.pricing.domain.exception.CurrencyException;
import com.inditex.pricing.domain.exception.DateException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public class Price {

  private final Integer priceList;
  private final Integer productId;
  private final Integer brandId;
  private final String currency;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Integer priority;
  private final BigDecimal price;

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
    if(startDate.isAfter(endDate))
      throw new DateException(startDate, endDate);
  }

  private String checkCurrency(String currency) {
    for (Currency availableCurrencies: Currency.getAvailableCurrencies()) {
      if(availableCurrencies.getCurrencyCode().equals(currency))
        return currency;
    }
    throw new CurrencyException(currency);
  }

  public Integer priceList() {
    return priceList;
  }

  public Integer productId() {
    return productId;
  }

  public Integer brandId() {
    return brandId;
  }

  public String currency() {
    return currency;
  }

  public LocalDateTime startDate() {
    return startDate;
  }

  public LocalDateTime endDate() {
    return endDate;
  }

  public Integer priority() {
    return priority;
  }

  public BigDecimal price() {
    return price;
  }

}
