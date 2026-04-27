package kz.trendprice.server.parserservice.mapping.repository;

import kz.trendprice.server.parserservice.mapping.entity.StoreProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoreProductMappingRepository extends JpaRepository<StoreProductMapping, Long> {
    StoreProductMapping findByStoreIdAndExternalProductId(UUID storeId, String externalProductId);
}
