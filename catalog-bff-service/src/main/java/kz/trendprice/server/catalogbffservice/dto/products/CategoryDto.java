package kz.trendprice.server.catalogbffservice.dto.products;

import java.time.Instant;

public record CategoryDto (
        String id,
        String title,
        Instant createdAt,
        Instant updatedAt
) {}
