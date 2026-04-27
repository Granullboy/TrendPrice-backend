package kz.trendprice.server.parserservice.dto.products;

import java.time.Instant;

public record CategoryDto (
        String id,
        String title,
        Instant createdAt,
        Instant updatedAt
) {}
