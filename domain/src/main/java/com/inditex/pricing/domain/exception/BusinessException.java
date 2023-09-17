package com.inditex.pricing.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
  private final String code;

  BusinessException(String message, String code) {
    super(message);
    this.code = code;
  }
}
