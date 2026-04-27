package kz.trendprice.server.parserservice.dto.products;

import java.time.Instant;
import java.util.List;

public record ProductDto (
    String id,
    String title,
    String type,
    BrandDto brand,
    String barcode,
    List<CategoryDto> categories,
    Instant createdAt,
    Instant updatedAt
) {}
