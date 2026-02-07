package kz.trendprice.server.catalogbffservice.view;

import kz.trendprice.server.catalogbffservice.dto.prices.PriceDto;
import kz.trendprice.server.catalogbffservice.dto.products.ProductDto;

import java.util.List;

public record ProductPriceViewWithCategory (
   ProductDto product,
   List<String> categories,
   List<PriceDto> prices,
   PriceDto bestPrice
) {}
