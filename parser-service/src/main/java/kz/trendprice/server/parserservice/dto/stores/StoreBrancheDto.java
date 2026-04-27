package kz.trendprice.server.parserservice.dto.stores;

import java.time.Instant;

public record StoreBrancheDto(
        String id,
        StoreDto store,
        StoreStatusDto status,
        String openHours,
        Double latitude,
        Double longitude,
        Instant createdAt,
        Instant updatedAt
) {}