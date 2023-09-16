package com.inditex.pricing.adaptor.database.specification;

import com.inditex.pricing.adaptor.database.entity.PriceEntity;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PriceSpecification {
  public Specification<PriceEntity> equal(String param, Integer value) {
    return (root, query, cb) -> cb.equal(root.get(param), value);
  }

  public Specification<PriceEntity> betweenDates(LocalDateTime date) {
    return (root, query, cb) -> {
      var predicates = new ArrayList<Predicate>();
      predicates.add(cb.lessThan(root.get("startDate"), date));
      predicates.add(cb.greaterThan(root.get("endDate"), date));
      return cb.and(predicates.toArray(Predicate[]::new));
    };
  }

  public Specification<PriceEntity> getMaxPriorityProductSpecification(
      Integer brandId, Integer productId, LocalDateTime date) {
    return (root, query, criteriaBuilder) -> {
      var maxPrioritySubquery = query.subquery(Integer.class);
      var maxPriorityProductRoot = maxPrioritySubquery.from(PriceEntity.class);
      maxPrioritySubquery.select(criteriaBuilder.max(maxPriorityProductRoot.get("priority")));
      maxPrioritySubquery.where(
          criteriaBuilder.and(
              criteriaBuilder.equal(maxPriorityProductRoot.get("brandId"), brandId),
              criteriaBuilder.equal(maxPriorityProductRoot.get("productId"), productId),
              criteriaBuilder.lessThan(maxPriorityProductRoot.get("startDate"), date),
              criteriaBuilder.greaterThan(maxPriorityProductRoot.get("endDate"), date)));

      return criteriaBuilder.equal(root.get("priority"), maxPrioritySubquery);
    };
  }
}
