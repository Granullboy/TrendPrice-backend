package kz.trendprice.server.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "categories",
        indexes = {
                @Index(name = "idx_order_categories_title", columnList = "title")
        }
)
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "title", nullable = false, length = 100, unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
    @JsonIgnoreProperties("categories")
    private List<Product> products;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
