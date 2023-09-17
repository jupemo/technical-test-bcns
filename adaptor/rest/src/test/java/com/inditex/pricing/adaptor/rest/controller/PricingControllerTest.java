package com.inditex.pricing.adaptor.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.adaptor.rest.mapper.PriceResponseMapperImpl;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery.Command;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery.PriceResult;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@ContextConfiguration(classes = PricingController.class)
class PricingControllerTest {

  @Autowired MockMvc mockMvc;
  @Autowired ObjectMapper objectMapper;
  @MockBean GetCorrectPriceQuery getCorrectPriceQuery;
  @SpyBean PriceResponseMapperImpl priceResponseMapper;

  @Test
  void shouldCallGetCorrectPricingQuery_priceRequestComplete() throws Exception {
    var request =
        MockMvcRequestBuilders.get("/prices/products/1/brands/1?date=" + LocalDateTime.now())
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE);

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

    verify(getCorrectPriceQuery, times(1)).retrievePrice(any());
  }

  @Test
  void shouldCallGetCorrectPricingQuery_priceRequestNullDate() throws Exception {

    var request =
        MockMvcRequestBuilders.get("/prices/products/1/brands/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE);

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

    verify(getCorrectPriceQuery, times(1)).retrievePrice(any());
  }

  @Test
  void shouldReturnOneElement() throws Exception {
    var date = LocalDateTime.of(2020, 10, 1, 1, 1);
    var command = new Command(date, 1, 1);
    var priceResult =
        new PriceResult(1, LocalDateTime.now(), LocalDateTime.now(), 1, 1, BigDecimal.valueOf(2.1));

    when(getCorrectPriceQuery.retrievePrice(command)).thenReturn(priceResult);
    var request =
        MockMvcRequestBuilders.get("/prices/products/1/brands/1?date=" + date)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE);

    var response =
        mockMvc
            .perform(request)
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse();

    assertNotNull(response);
    var priceResponse = objectMapper.readValue(response.getContentAsString(), PriceResponse.class);
    assertAll(
        () -> assertEquals(priceResult.price(), priceResponse.price(), () -> "price"),
        () -> assertEquals(priceResult.priceList(), priceResponse.priceList(), () -> "price list"),
        () -> assertEquals(priceResult.brandId(), priceResponse.brandId(), () -> "brand id"),
        () -> assertEquals(priceResult.endDate(), priceResponse.endDate(), () -> "end date"),
        () -> assertEquals(priceResult.startDate(), priceResponse.startDate(), () -> "start date"));
  }
}
