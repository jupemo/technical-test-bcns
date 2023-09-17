package com.inditex.pricing.application.port.input.interaptor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.inditex.pricing.application.exception.NotFoundException;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery.Command;
import com.inditex.pricing.application.port.input.GetCorrectPriceQuery.PriceResult;
import com.inditex.pricing.application.port.input.mapper.PriceMapper;
import com.inditex.pricing.application.port.output.LoadPricePort;
import com.inditex.pricing.domain.Price;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCorrectPriceQueryServiceTest {

  @Mock private LoadPricePort loadPricePort;
  @Mock private PriceMapper priceMapper;
  @Mock private Price price;
  @InjectMocks private GetCorrectPriceQueryService testUnit;

  @Test
  @DisplayName("Should retrieve correct price")
  void shouldRetrievePrice() {
    var date = LocalDateTime.now();
    var command = new Command(date, 1, 1);
    var priceValue =
        new PriceResult(1, date.minusDays(1), date.plusDays(1), 1, 1, BigDecimal.valueOf(22.1));

    when(loadPricePort.getPrice(date, 1, 1)).thenReturn(Optional.of(price));
    when(priceMapper.map(price)).thenReturn(priceValue);

    testUnit.retrievePrice(command);

    verify(loadPricePort, times(1)).getPrice(date, 1, 1);
    verify(priceMapper, times(1)).map(price);
  }

  @Test
  @DisplayName("Should Throw not found exception when port return empty")
  void shouldThrowsNotFoundException() {
    var date = LocalDateTime.now();
    var command = new Command(date, 1, 1);

    when(loadPricePort.getPrice(date, 1, 1)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () ->testUnit.retrievePrice(command));
  }

}
