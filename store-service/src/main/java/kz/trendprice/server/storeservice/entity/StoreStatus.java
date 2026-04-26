package kz.trendprice.server.storeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="store_statuses")
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class StoreStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "store_status_id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;
}
