package kz.trendprice.server.priceservice.entity;

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
                @Index(name = "idx_prices_store_city_time", columnList = "store_id,city,time"),
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
    private UUID id;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "unit_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal unitAmount;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "price_per_unit", precision = 19, scale = 4)
    private BigDecimal pricePerUnit;

    @Column(name = "currency")
    private String currency;

    @Column(name = "city")
    private String city;

    @Column(name = "final_price", updatable = false, precision = 19, scale = 4)
    private BigDecimal finalPrice;

    @Column(name = "time", nullable = false)
    private Instant time;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
