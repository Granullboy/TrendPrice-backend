package kz.trendprice.server.storeservice.repository;

import kz.trendprice.server.storeservice.entity.StoreStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoreStatusRepository extends JpaRepository<StoreStatus, UUID> {
    List<StoreStatus> findByTitleContainingIgnoreCase(String title);
}
