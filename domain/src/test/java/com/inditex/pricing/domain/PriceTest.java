package com.inditex.pricing.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.inditex.pricing.domain.exception.DateException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PriceTest {

  public static final String CURRENCY_EUR = "EUR";
  public static final LocalDateTime START_DATE = LocalDateTime.of(2022, 10, 1, 0, 0, 21, 0);
  public static final LocalDateTime END_DATE = LocalDateTime.of(2022, 11, 1, 0, 0, 21, 0);

  @ParameterizedTest
  @MethodSource("validValues")
  void shouldCreatePriceWithCorrectValues(String currency, LocalDateTime startDate, LocalDateTime endDate) {
    assertDoesNotThrow(
        () ->
            new Price(
                1,
                1,
                1,
                currency,
                startDate,
                endDate,
                1,
                BigDecimal.valueOf(10.2)));
  }


  @Test
  void shouldThrowException_currencyNotValid_whenCurrencyRRR() {
    String currency = "RRR";

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new Price(
                1,
                1,
                1,
                currency,
                START_DATE,
                END_DATE,
                1,
                BigDecimal.valueOf(10.2)));

  }

  @Test
  void shouldThrowException_dateException_whenEndDateIsBiggest() {
    assertThrows(
        DateException.class,
        () ->
            new Price(
                1,
                1,
                1,
                CURRENCY_EUR,
                END_DATE,
                START_DATE,
                1,
                BigDecimal.valueOf(10.2)));
  }


  private static Stream<Arguments> validValues() {
    return Stream.of(
        Arguments.of(CURRENCY_EUR, START_DATE, END_DATE),
        Arguments.of(Currency.getInstance(Locale.US).getCurrencyCode(), START_DATE, END_DATE),
        Arguments.of(Currency.getInstance(Locale.UK).getCurrencyCode(), START_DATE, END_DATE),
        Arguments.of(CURRENCY_EUR, START_DATE, START_DATE)
    );
  }

}
