package kz.trendprice.server.priceservice.controller;

import com.fasterxml.jackson.annotation.JsonView;
import kz.trendprice.server.priceservice.entity.Price;
import kz.trendprice.server.priceservice.entity.Views;
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
    @JsonView(Views.Internal.class)
    public ResponseEntity<Price> getPrice(@PathVariable UUID id) {
        Price price = priceService.getPrice(id);
        return (price != null) ? ResponseEntity.ok(price) : ResponseEntity.notFound().build();
    }

    @GetMapping("/store/{store_id}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<List<Price>> getPricesByStoreId(@PathVariable UUID store_id) {
        List<Price> prices = priceService.getPricesByStoreId(store_id);
        return (prices != null) ? ResponseEntity.ok(prices) : ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{product_id}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<List<Price>> getPricesByProductId(@PathVariable UUID product_id) {
        List<Price> prices = priceService.getPricesByProductId(product_id);
        System.out.println(prices.isEmpty());
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/product/{product_id}/days/{days_amount}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<List<Price>> getPricesByProductIdLastWeek(@PathVariable UUID product_id, @PathVariable int days_amount) {
        List<Price> prices = priceService.getPricesByProductIdLastDayAmount(product_id, days_amount);
        System.out.println(prices.isEmpty());
        return ResponseEntity.ok(prices);
    }

    @GetMapping("/store/{store_id}/product/{product_id}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<List<Price>> getPricesByStoreIdAndProductId(@PathVariable UUID store_id, @PathVariable UUID product_id) {
        List<Price> prices = priceService.getPricesByStoreIdAndProductId(store_id, product_id);
        return (prices != null) ? ResponseEntity.ok(prices) : ResponseEntity.notFound().build();
    }

    @GetMapping("/best/{product_id}/{city}")
    @JsonView(Views.Internal.class)
    public ResponseEntity<Price> getBestPriceByProductIdAndCity(@PathVariable UUID product_id, @PathVariable String city) {
        System.out.println("productId" + product_id);
        System.out.println("city" + city);
        Price price = priceService.getBestPriceByProductIdAndCity(product_id, city);
        return (price != null) ? ResponseEntity.ok(price) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        Price newPrice = priceService.createPrice(price);
        return (newPrice != null) ? ResponseEntity.ok(newPrice) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable UUID id, @RequestBody Price price) {
        System.out.println(price);
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
