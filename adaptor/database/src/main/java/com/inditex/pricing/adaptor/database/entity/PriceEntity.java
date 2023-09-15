package com.inditex.pricing.adaptor.database.entity;

import com.inditex.pricing.adaptor.database.entity.PriceEntity.PriceId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "prices")
@IdClass(PriceId.class)
public class PriceEntity {

  @Id
  @Column(name = "product_id")
  Integer productId;

  @Id
  @Column(name = "price_list")
  @NotNull
  Integer priceList;

  @Column(name = "brand_id")
  @NotNull
  Integer brandId;

  @Column(name = "priority")
  @NotNull
  Integer priority;

  @Column(name = "currency")
  @NotNull
  @Size(max = 3)
  String Currency;

  @Column(name = "price")
  @NotNull
  BigDecimal price;

  @Column(name = "start_date")
  @NotNull
  LocalDateTime startDate;

  @Column(name = "end_date")
  @NotNull
  LocalDateTime endDate;

  static class PriceId implements Serializable {
    private Integer productId;
    private Integer priceList;
  }
  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy
            ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
            : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy
            ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
            : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) return false;
    PriceEntity that = (PriceEntity) o;
    return getProductId() != null
        && Objects.equals(getProductId(), that.getProductId())
        && getPriceList() != null
        && Objects.equals(getPriceList(), that.getPriceList());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(productId, priceList);
  }
}
