package com.inditex.pricing.adaptor.database.specification;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecification {
  public Specification<PriceEntity> equal(String param, Integer value) {
    return (dbEntityRoot, query, cb) -> cb.equal(dbEntityRoot.get(param), value);
  }

  public Specification<PriceEntity> betweenDates(LocalDateTime date) {
    return (dbEntityRoot, query, cb) -> {
      var predicates = new ArrayList<Predicate>();
      predicates.add(cb.lessThan(dbEntityRoot.get("startDate"), date));
      predicates.add(cb.greaterThan(dbEntityRoot.get("endDate"), date));
      return cb.and(predicates.toArray(Predicate[]::new));
    };
  }
}
