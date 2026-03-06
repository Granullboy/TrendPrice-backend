package kz.trendprice.server.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(
        name = "brands",
        indexes = {
                @Index(name = "idx_order_brand_id", columnList = "brand_id"),
                @Index(name = "idx_order_title", columnList = "title")
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "brand_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
