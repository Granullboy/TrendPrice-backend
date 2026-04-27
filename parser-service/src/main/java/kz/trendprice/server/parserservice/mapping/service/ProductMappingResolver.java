package kz.trendprice.server.parserservice.mapping.service;

import kz.trendprice.server.parserservice.mapping.entity.StoreProductMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductMappingResolver {

    private final StoreProductMappingService storeProductMappingService;

    public String resolveProductId(UUID storeId, String externalProductId) {
        StoreProductMapping mapping = storeProductMappingService
                .getStoreProductMappingByStoreIdAndProductId(storeId, externalProductId);

        if (mapping == null || mapping.getProductId() == null) {
            return "-1";
        }

        return mapping.getProductId().toString();
    }
}
