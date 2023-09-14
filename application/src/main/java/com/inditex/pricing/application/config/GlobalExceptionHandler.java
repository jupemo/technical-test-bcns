package com.inditex.pricing.application.config;

import com.inditex.pricing.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handle(BusinessException e) {
    return new ErrorDTO(e.getMessage(), e.getCode());
  }

  public record ErrorDTO(String message, String code) {}
}
