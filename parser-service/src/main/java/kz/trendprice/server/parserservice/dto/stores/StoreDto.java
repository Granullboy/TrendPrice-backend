package kz.trendprice.server.parserservice.dto.stores;

import java.time.Instant;

public record StoreDto(
        String id,
        String title,
        String description,
        String contactInfo,
        Instant createdAt,
        Instant updatedAt
) {}