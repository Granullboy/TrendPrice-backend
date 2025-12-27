package kz.trendprice.server.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

// Entity for stores in database
@Entity
@Table(
        name="stores",
        indexes = {
                @Index(name = "idx_order_store_id", columnList = "store_id") //indexing stores by id
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_id")
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "contact_info")
    private String contactInfo;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
