package kz.trendprice.server.storeservice.repository;

import kz.trendprice.server.storeservice.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    List<Store> findByTitleContainingIgnoreCase(String title);
}
