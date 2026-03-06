package kz.trendprice.server.parserservice.mapping.controller;

import kz.trendprice.server.parserservice.mapping.entity.StoreProductMapping;
import kz.trendprice.server.parserservice.mapping.service.StoreProductMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mappings")
public class MappingController {
    private final StoreProductMappingService storeProductMappingService;

    @GetMapping("/store_product")
    public ResponseEntity<List<StoreProductMapping>> getMappings() {
        return ResponseEntity.ok(storeProductMappingService.getStoreProductMappings());
    }

    @GetMapping("/store_product/{id}")
    public ResponseEntity<StoreProductMapping> getMappingById(@PathVariable Long id) {
        return ResponseEntity.ok(storeProductMappingService.getStoreProductMapping(id));
    }

    @PostMapping("/store_product")
    public ResponseEntity<StoreProductMapping> createMapping(@RequestBody StoreProductMapping storeProductMapping) {
        return ResponseEntity.ok(storeProductMappingService.saveStoreProductMapping(storeProductMapping));
    }

    @PutMapping("/store_product")
    public ResponseEntity<StoreProductMapping> updateMapping(@RequestBody StoreProductMapping storeProductMapping) {
        return ResponseEntity.ok(storeProductMappingService.updateStoreProductMapping(storeProductMapping));
    }

    @DeleteMapping("/store_product/{id}")
    public ResponseEntity<StoreProductMapping> deleteMapping(@PathVariable Long id) {
        storeProductMappingService.deleteStoreProductMapping(id);
        return ResponseEntity.noContent().build();
    }
}
