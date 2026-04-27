package kz.trendprice.server.purchaseservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "reciepts",
        indexes = {
                @Index(name = "idx_order_reciept_id", columnList = "reciept_id")
        }
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Reciept {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reciept_id", nullable = false, updatable = false)
    private UUID id;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Purchase> purchases;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "price_value")
    private double priceValue;

    @Column(name = "reciept_date")
    private Date recieptDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
