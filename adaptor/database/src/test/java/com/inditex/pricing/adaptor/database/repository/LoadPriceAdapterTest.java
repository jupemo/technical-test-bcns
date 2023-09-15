package com.inditex.pricing.adaptor.database.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LoadPriceAdapterTest {

  @Autowired
  private LoadPriceAdapter loadPriceAdapter;

  @Test
  void test() {
    loadPriceAdapter.getPrice(LocalDateTime.of(2020, 6, 14, 11,0,0), 35455, 1);
  }


}
