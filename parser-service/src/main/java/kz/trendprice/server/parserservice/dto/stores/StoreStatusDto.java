package kz.trendprice.server.parserservice.dto.stores;

import java.time.Instant;

public record StoreStatusDto(
        String id,
        String title,
        Instant createdAt,
        Instant updatedAt
) {}