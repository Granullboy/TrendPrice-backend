package kz.trendprice.server.storeservice.repository;

import kz.trendprice.server.storeservice.entity.StoreBranche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface StoreBrancheRepository extends JpaRepository<StoreBranche, UUID>, JpaSpecificationExecutor<StoreBranche> {
}
