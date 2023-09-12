package com.inditex.pricing.domain.exception;

public class BusinessException extends RuntimeException {
  private String code;

  BusinessException(String message, String code) {
    super(message);
    this.code = code;
  }

}
