package kz.trendprice.server.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "store_branches",
        indexes = {
                @Index(name = "idx_store_branches_store_id", columnList = "store_id"),
                @Index(name = "idx_store_branches_status_id", columnList = "status_id"),
                @Index(name = "idx_store_branches_store_status", columnList = "store_id, status_id")
        }
)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class StoreBranche {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private StoreStatus status;

    @Column(name = "open_hours")
    private String openHours;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longtitude")
    private Double longitude;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
