package com.inditex.pricing.application.config;

import com.inditex.pricing.application.exception.NotFoundException;
import com.inditex.pricing.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
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

  @ExceptionHandler(NotFoundException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorDTO handle(NotFoundException e) {
    return new ErrorDTO(e.getMessage(), e.getCode());
  }

  @ExceptionHandler(TypeMismatchException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorDTO handle(TypeMismatchException e) {
    var message =
        String.format(
            "Bad parameter type '%s' required type '%s'", e.getPropertyName(), e.getRequiredType());
    return new ErrorDTO(message, "V0001");
  }

  public record ErrorDTO(String message, String code) {}
}
