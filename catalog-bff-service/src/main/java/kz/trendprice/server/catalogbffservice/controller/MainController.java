package kz.trendprice.server.catalogbffservice.controller;

import kz.trendprice.server.catalogbffservice.service.MainService;
import kz.trendprice.server.catalogbffservice.view.ProductPriceViewWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalog")
public class MainController {

    private final MainService mainService;

    @GetMapping("/isAlive")
    public ResponseEntity<Mono<Map<String, String>>> isAlive() {
        return ResponseEntity.ok(mainService.isAlive());
    }

    @GetMapping("/products/{productId}/prices/{dayAmount}")
    public Mono<ProductPriceViewWithCategory> getPrice(@PathVariable String productId, @PathVariable int dayAmount) {
        System.out.println("productId: " + productId + "\ndayAmount: " + dayAmount);
        return mainService.getProductPriceViewWithCategory(productId, dayAmount);
    }
}
