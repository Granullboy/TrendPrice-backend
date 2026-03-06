package kz.trendprice.server.productservice.repository;

import kz.trendprice.server.productservice.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
