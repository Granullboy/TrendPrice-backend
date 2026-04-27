package kz.trendprice.server.parserservice.dto.prices;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceDto(
    String id,
    String productId,
    String storeId,
    BigDecimal unitAmount,
    String unit,
    BigDecimal pricePerUnit,
    String currency,
    String city,
    BigDecimal discount,
    BigDecimal finalPrice,
    Instant time,
    Instant createdAt,
    Instant updatedAt
) {}
