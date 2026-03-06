package kz.trendprice.server.parserservice.mapping.service;

import kz.trendprice.server.parserservice.mapping.entity.StoreProductMapping;
import kz.trendprice.server.parserservice.mapping.repository.StoreProductMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreProductMappingService {
    private final StoreProductMappingRepository storeProductMappingRepository;

    public List<StoreProductMapping> getStoreProductMappings() {
        return storeProductMappingRepository.findAll();
    }

    public StoreProductMapping getStoreProductMapping(Long storeProductMappingId) {
        return storeProductMappingRepository.findById(storeProductMappingId).orElse(null);
    }

    public StoreProductMapping getStoreProductMappingByStoreIdAndProductId(UUID storeId, UUID productId) {
        return storeProductMappingRepository.findByStoreIdAndAndExternalProductId(storeId, productId);
    }

    public StoreProductMapping saveStoreProductMapping(StoreProductMapping storeProductMapping) {
        return storeProductMappingRepository.save(storeProductMapping);
    }

    public StoreProductMapping updateStoreProductMapping(StoreProductMapping storeProductMapping) {
        return storeProductMappingRepository.save(storeProductMapping);
    }

    public void deleteStoreProductMapping(Long storeProductMappingId) {
        storeProductMappingRepository.deleteById(storeProductMappingId);
    }
}
