package com.inditex.pricing.adaptor.database.repository.jpa;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpringDataPriceRepository
    extends JpaRepository<PriceEntity, Integer>, JpaSpecificationExecutor<PriceEntity> {}
