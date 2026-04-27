package kz.trendprice.server.purchaseservice.repository;

import kz.trendprice.server.purchaseservice.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
