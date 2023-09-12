package com.inditex.pricing.domain.exception;

import java.time.LocalDateTime;

public class DateException extends BusinessException {
  private static final String MESSAGE_FORMAT = "Start date '%s' must not be before end date '%s'";
  private static final String code = "B001";

  public DateException(LocalDateTime startDate, LocalDateTime endDate) {
    super(String.format(MESSAGE_FORMAT, startDate.toString(), endDate.toString()), code);
  }
}
