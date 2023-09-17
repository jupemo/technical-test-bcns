package com.inditex.pricing.main;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.application.config.GlobalExceptionHandler.ErrorDTO;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ActiveProfiles("integration-test")
@AutoConfigureMockMvc
public class GetPriceIT {

  private static final String PRICES_URL_FORMAT = "/prices/products/%d/brands/%d";
  private static final int PRODUCT_ID = 35455;
  private static final int BRAND_ID = 1;

  @Autowired MockMvc mvc;

  @Autowired ObjectMapper objectMapper;

  @ParameterizedTest
  @MethodSource("requestList")
  @DisplayName("Should return price in all scenarios")
  void shouldReturnPrice(LocalDateTime date, PriceResponse response) throws Exception {
    whenEndpointCalled(date)
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
  }

  @Test
  @DisplayName("Should return Not Found when price not found")
  void shouldReturnNotFoundException() throws Exception {
    var date = getLocalDateTime(1, 1, 10, 0, 0);
    var errorMessage =
        String.format(
            "Price for product id '%d' and brand id '%d' not found.", PRODUCT_ID, BRAND_ID);
    var code = "B0003";
    var error = new ErrorDTO(errorMessage, code);
    whenEndpointCalled(date)
        .andExpect(status().isNotFound())
        .andExpect(content().json(objectMapper.writeValueAsString(error)));
  }

  @Test
  @DisplayName("Should return Bad Request when the URI is bad formatted")
  void shouldReturnBadRequestException() throws Exception {
    var date = getLocalDateTime(1, 1, 10, 0, 0);
    var errorMessage =
            "Bad parameter type 'productId' required type 'class java.lang.Integer'";
    var code = "V0001";
    var error = new ErrorDTO(errorMessage, code);
    var url = "/prices/products/test/brands/test";
    mvc.perform(get(url).param("date", String.valueOf(date)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json(objectMapper.writeValueAsString(error)));
  }

  private static Stream<Arguments> requestList() {
    return Stream.of(
        Arguments.of(
            getLocalDateTime(6, 14, 10, 0, 0),
            getPriceResponse(
                getLocalDateTime(6, 14, 0, 0, 0),
                getLocalDateTime(12, 31, 23, 59, 59),
                1,
                BigDecimal.valueOf(35.50))),
        Arguments.of(
            getLocalDateTime(6, 14, 16, 0, 0),
            getPriceResponse(
                getLocalDateTime(6, 14, 15, 0, 0),
                getLocalDateTime(6, 14, 18, 30, 0),
                2,
                BigDecimal.valueOf(25.45))),
        Arguments.of(
            getLocalDateTime(6, 14, 21, 0, 0),
            getPriceResponse(
                getLocalDateTime(6, 14, 0, 0, 0),
                getLocalDateTime(12, 31, 23, 59, 59),
                1,
                BigDecimal.valueOf(35.50))),
        Arguments.of(
            getLocalDateTime(6, 15, 10, 0, 0),
            getPriceResponse(
                getLocalDateTime(6, 15, 0, 0, 0),
                getLocalDateTime(6, 15, 11, 0, 0),
                3,
                BigDecimal.valueOf(30.50))),
        Arguments.of(
            getLocalDateTime(6, 16, 21, 0, 0),
            getPriceResponse(
                getLocalDateTime(6, 15, 16, 0, 0),
                getLocalDateTime(12, 31, 23, 59, 59),
                4,
                BigDecimal.valueOf(38.95))));
  }

  private static LocalDateTime getLocalDateTime(
      Integer month, Integer day, Integer hour, Integer min, Integer sec) {
    return LocalDateTime.of(2020, month, day, hour, min, sec);
  }

  private static PriceResponse getPriceResponse(
      LocalDateTime startDate, LocalDateTime endDateTime, Integer priceList, BigDecimal price) {
    return new PriceResponse(PRODUCT_ID, startDate, endDateTime, priceList, BRAND_ID, price);
  }

  private ResultActions whenEndpointCalled(LocalDateTime date) throws Exception {
    var url = String.format(PRICES_URL_FORMAT, PRODUCT_ID, BRAND_ID);
    var getWithParam = get(url).param("date", String.valueOf(date));
    return mvc.perform(getWithParam).andDo(print());
  }
}
