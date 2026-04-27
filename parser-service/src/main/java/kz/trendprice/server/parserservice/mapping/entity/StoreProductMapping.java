package kz.trendprice.server.parserservice.mapping.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "store_product_mapping")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class StoreProductMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long id;

    @Column(name = "store_id")
    private UUID storeId;

    @Column(name = "external_product_id")
    private String externalProductId ;

    @Column(name = "product_id")
    private UUID productId;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
