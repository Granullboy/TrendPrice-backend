package kz.trendprice.server.priceservice.repository;

import kz.trendprice.server.priceservice.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
    List<Price> findAllByProductIdAndStoreIdOrderByTimeDesc (UUID product_id, UUID store_id);
    List<Price> findAllByProductIdOrderByTimeDesc (UUID product_id);
    List<Price> findAllByStoreIdOrderByTimeDesc (UUID store_id);
    List<Price> findAllByOrderByTimeDesc ();
    @Query(value = """
        SELECT p.*
        FROM prices p
        WHERE p.product_id = :productId
          AND lower(p.city) = lower(:city)
        ORDER BY p.time DESC, p.price_per_unit ASC
        LIMIT 1
    """, nativeQuery = true)
    Price findBestPriceByProductIdAndCity(@Param("productId") UUID product_id, @Param("city") String city);

    @Query("""
       SELECT p
       FROM Price p
       WHERE p.productId = :productId
         AND p.time >= :cutoff
       ORDER BY p.time DESC
    """)
    List<Price> findAllByProductIdLastWeekOrder (UUID product_id);
}
