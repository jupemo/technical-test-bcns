package com.inditex.pricing.adaptor.database.repository.jpa;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringBootPriceRepository extends JpaRepository<PriceEntity, Integer> {
  PriceEntity findByProductIdAndBrandIdAndStartDateBeforeAndEndDateAfter(
      Integer productId,
      @NotNull Integer brandId,
      @NotNull LocalDateTime startDate,
      @NotNull LocalDateTime endDate);

}
