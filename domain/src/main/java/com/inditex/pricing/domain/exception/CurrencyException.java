package com.inditex.pricing.domain.exception;

public class CurrencyException extends BusinessException {

  private static final String MESSAGE_FORMAT = "'%s' is not a valid currency";

  private static final String CODE = "B002";

  public CurrencyException(String currency) {
    super(String.format(MESSAGE_FORMAT, currency), CODE);
  }
}
