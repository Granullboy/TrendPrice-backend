package kz.trendprice.server.purchaseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "purchases",
        indexes = {
                @Index(name = "idx_order_purchase_id", columnList = "purchase_id")
        }
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "purchase_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reciept_id")
    private Reciept reciept;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "price_id")
    private UUID priceId;

    @Column(name = "price_value")
    private double priceValue;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
