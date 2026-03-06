package kz.trendprice.server.priceservice.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "prices",
        indexes = {
                @Index(name = "idx_order_price_id", columnList = "price_id"),
                @Index(name = "idx_prices_product_time", columnList = "product_id,time"),
                @Index(name = "idx_prices_store_city_time", columnList = "store_id,city,time"),
                @Index(name = "idx_prices_product_price_city_time", columnList = "product_id,final_price,city,time"),
                @Index(name = "idx_prices_product_city_time", columnList = "product_id,city,time"),
                @Index(name = "idx_prices_store_product_time", columnList = "product_id,store_id,time"),
                @Index(name = "idx_prices_product_time", columnList = "product_id,time"),
                @Index(name = "idx_prices_store_time", columnList = "store_id,time"),
                @Index(name = "idx_prices_time", columnList = "time")
        }
)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView(Views.Public.class)
    private UUID id;

    @Column(name = "product_id", nullable = false)
    @JsonView(Views.Public.class)
    private UUID productId;

    @Column(name = "store_id", nullable = false)
    @JsonView(Views.Public.class)
    private UUID storeId;

    @Column(name = "unit_amount", nullable = false, precision = 19, scale = 4)
    @JsonView(Views.Internal.class)
    private BigDecimal unitAmount;

    @Column(name = "unit", nullable = false)
    @JsonView(Views.Internal.class)
    private String unit;

    @Column(name = "price_per_unit", precision = 19, scale = 4)
    @JsonView(Views.Internal.class)
    private BigDecimal pricePerUnit;

    @Column(name = "currency")
    @JsonView(Views.Internal.class)
    private String currency;

    @Column(name = "city")
    @JsonView(Views.Internal.class)
    private String city;

    @Column(name = "discount")
    @JsonView(Views.Internal.class)
    private BigDecimal discount;

    @Column(name = "final_price", updatable = false, precision = 19, scale = 4)
    @JsonView(Views.Public.class)
    private BigDecimal finalPrice;

    @Column(name = "time", nullable = false)
    @JsonView(Views.Public.class)
    private Instant time;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @JsonView(Views.Public.class)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonView(Views.Public.class)
    private Date updatedAt;
}
