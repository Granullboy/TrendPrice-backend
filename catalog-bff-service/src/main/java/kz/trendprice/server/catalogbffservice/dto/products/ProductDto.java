package kz.trendprice.server.catalogbffservice.dto.products;

import java.time.Instant;
import java.util.List;

public record ProductDto (
    String id,
    String title,
    String type,
    String brand,
    String barcode,
    List<CategoryDto> categories,
    Instant createdAt,
    Instant updatedAt
) {}
