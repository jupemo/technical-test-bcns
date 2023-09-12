package com.inditex.pricing.domain.exception;

public class DateException extends RuntimeException{
  private String message;
  private String code;
  public DateException(String message, String code) {
    super(message);
    this.code = code;
  }
}
