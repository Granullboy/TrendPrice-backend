package kz.trendprice.server.purchaseservice.repository;

import kz.trendprice.server.purchaseservice.entity.Reciept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecieptRepository extends JpaRepository<Reciept, UUID> {
}
