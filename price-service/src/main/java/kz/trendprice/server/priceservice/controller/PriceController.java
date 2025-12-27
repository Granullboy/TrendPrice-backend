package kz.trendprice.server.priceservice.controller;

import kz.trendprice.server.priceservice.entity.Price;
import kz.trendprice.server.priceservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping
    public ResponseEntity<List<Price>> getPrices() {
        return ResponseEntity.ok(priceService.getPrices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> getPrice(@PathVariable UUID id) {
        Price price = priceService.getPrice(id);
        return (price != null) ? ResponseEntity.ok(price) : ResponseEntity.notFound().build();
    }

    @GetMapping("/store/{store_id}")
    public ResponseEntity<List<Price>> getPricesByStoreId(@PathVariable UUID storeId) {
        List<Price> prices = priceService.getPricesByStoreId(storeId);
        return (prices != null) ? ResponseEntity.ok(prices) : ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{product_id}")
    public ResponseEntity<List<Price>> getPricesByProductId(@PathVariable UUID productId) {
        List<Price> prices = priceService.getPricesByProductId(productId);
        return (prices != null) ? ResponseEntity.ok(prices) : ResponseEntity.notFound().build();
    }

    @GetMapping("/store/{store_id}/product/{product_id}")
    public ResponseEntity<List<Price>> getPricesByStoreIdAndProductId(@PathVariable UUID storeId, @PathVariable UUID productId) {
        List<Price> prices = priceService.getPricesByStoreIdAndProductId(storeId, productId);
        return (prices != null) ? ResponseEntity.ok(prices) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        Price newPrice = priceService.createPrice(price);
        return (newPrice != null) ? ResponseEntity.ok(newPrice) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable UUID id, @RequestBody Price price) {
        try {
            Price updatedPrice = priceService.updatePrice(id, price);
            return (updatedPrice != null) ? ResponseEntity.ok(updatedPrice) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(price);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable UUID id) {
        try {
            priceService.deletePrice(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
