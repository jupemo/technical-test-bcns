package com.inditex.pricing.adaptor.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LoadPriceAdapterTest {

  @Autowired private LoadPriceAdapter loadPriceAdapter;

  private static final Integer PRODUCT_ID = 35455;
  private static final Integer BRAND_ID = 1;

  @Test
  @DisplayName("Should get price with max priority when there are 2 equals product at same time")
  void shouldGetPriceWithMaxPriority_when2ProductAtSameTime() {
    var date = LocalDateTime.of(2020, 6, 14, 17, 0, 0);
    var result = loadPriceAdapter.getPrice(date, PRODUCT_ID, BRAND_ID);

    assertTrue(result.isPresent());
    assertEquals(1, result.get().priority());
    assertEquals(BRAND_ID, result.get().brandId());
    assertEquals(PRODUCT_ID, result.get().productId());
  }

  @Test
  @DisplayName("Should get price when there is 1 product at time")
  void shouldGetPriceWithMaxPriority_wheOnlyOneProductAtTime() {
    var date = LocalDateTime.of(2020, 6, 14, 10, 0, 0);
    var result = loadPriceAdapter.getPrice(date, PRODUCT_ID, BRAND_ID);

    assertTrue(result.isPresent());
    assertEquals(0, result.get().priority());
    assertEquals(BRAND_ID, result.get().brandId());
    assertEquals(PRODUCT_ID, result.get().productId());
  }

  @Test
  @DisplayName("Should get empty optional when no product is present")
  void shouldGetEmptyOptional_whenDataNotFound() {
    var date = LocalDateTime.of(2021, 6, 14, 10, 0, 0);
    var result = loadPriceAdapter.getPrice(date, PRODUCT_ID, BRAND_ID);

    assertFalse(result.isPresent());
  }
}
