package kz.trendprice.server.priceservice.repository;

import kz.trendprice.server.priceservice.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PriceRepository extends JpaRepository<Price, UUID> {
    List<Price> findAllByProductIdAndStoreIdOrderByTimeDesc (UUID product_id, UUID store_id);
    List<Price> findAllByProductIdOrderByTimeDesc (UUID product_id);
    List<Price> findAllByStoreIdOrderByTimeDesc (UUID store_id);
    List<Price> findAllByOrderByTimeDesc ();
}
