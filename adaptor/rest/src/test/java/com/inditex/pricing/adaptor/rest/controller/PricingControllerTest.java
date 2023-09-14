package com.inditex.pricing.adaptor.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.pricing.adaptor.rest.dto.PriceRequest;
import com.inditex.pricing.adaptor.rest.dto.PriceResponse;
import com.inditex.pricing.adaptor.rest.mapper.PriceRequestMapperImpl;
import com.inditex.pricing.adaptor.rest.mapper.PriceResponseMapperImpl;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery;
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
  @SpyBean PriceRequestMapperImpl priceRequestMapper;

  @Test
  void shouldCallGetCorrectPricingQuery_priceRequestComplete() throws Exception {
    var requestBody = new PriceRequest(LocalDateTime.now(), 1);

    var request =
        MockMvcRequestBuilders.get("/price/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestBody));

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

    verify(getCorrectPriceQuery, times(1)).retrievePrice(any());
  }

  @Test
  void shouldCallGetCorrectPricingQuery_priceRequestNullDate() throws Exception {
    var requestBody = new PriceRequest(null, 1);

    var request =
        MockMvcRequestBuilders.get("/price/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestBody));

    mockMvc.perform(request).andDo(print()).andExpect(status().isOk());

    verify(getCorrectPriceQuery, times(1)).retrievePrice(any());
  }


  @Test
  void shouldReturnBadRequest_priceRequestNullPriceId() throws Exception {
    var requestBody = new PriceRequest(LocalDateTime.now(), null);

    var request =
        MockMvcRequestBuilders.get("/price/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestBody));

    mockMvc.perform(request).andDo(print()).andExpect(status().isBadRequest());

    verify(getCorrectPriceQuery, times(0)).retrievePrice(any());
  }

  @Test
  void shouldReturnOneElement() throws Exception {
    var requestBody = new PriceRequest(LocalDateTime.now(), 1);
    var priceResult =
        new PriceResult(1, LocalDateTime.now(), LocalDateTime.now(), 1, 1, BigDecimal.valueOf(2.1));

    when(getCorrectPriceQuery.retrievePrice(priceRequestMapper.map(1, requestBody)))
        .thenReturn(priceResult);
    var request =
        MockMvcRequestBuilders.get("/price/1")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(objectMapper.writeValueAsString(requestBody));

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
