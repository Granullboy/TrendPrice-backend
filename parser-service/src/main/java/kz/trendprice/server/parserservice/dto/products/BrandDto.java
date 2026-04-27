package kz.trendprice.server.parserservice.dto.products;

import java.time.Instant;

public record BrandDto (
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {}
