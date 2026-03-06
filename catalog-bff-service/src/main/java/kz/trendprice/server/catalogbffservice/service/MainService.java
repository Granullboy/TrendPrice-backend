package kz.trendprice.server.catalogbffservice.service;

import kz.trendprice.server.catalogbffservice.client.PriceClient;
import kz.trendprice.server.catalogbffservice.client.ProductClient;
import kz.trendprice.server.catalogbffservice.client.StoreClient;
import kz.trendprice.server.catalogbffservice.dto.prices.PriceDto;
import kz.trendprice.server.catalogbffservice.dto.products.CategoryDto;
import kz.trendprice.server.catalogbffservice.dto.products.ProductDto;
import kz.trendprice.server.catalogbffservice.view.ProductPriceViewWithCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ProductClient productClient;
    private final PriceClient priceClient;
    private final StoreClient storeClient;

    public Mono<Map<String, String>> isAlive() {
        Mono<String> product = productClient.isAlive()
                .timeout(Duration.ofSeconds(2))
                .map(x -> "UP")
                .onErrorReturn("DOWN");

        Mono<String> price = priceClient.isAlive()
                .timeout(Duration.ofSeconds(2))
                .map(x -> "UP")
                .onErrorReturn("DOWN");

        Mono<String> store = storeClient.isAlive()
                .timeout(Duration.ofSeconds(2))
                .map(x -> "UP")
                .onErrorReturn("DOWN");

        return Mono.zip(product, price, store)
                .map(t -> {
                    Map<String, String> res = new LinkedHashMap<>();
                    res.put("catalog-service", "UP");
                    res.put("product-service", t.getT1());
                    res.put("price-service", t.getT2());
                    res.put("store-service", t.getT3());
                    return res;
                });
    }

    public Mono<ProductPriceViewWithCategory> getProductPriceViewWithCategory(String productId, Integer daysAmount) {
        System.out.println("productId: " + productId + "\ndaysAmount: " + daysAmount);
        Mono<ProductDto> productMono = productClient.getProduct(productId);
        Mono<List<PriceDto>> pricesMono =
                priceClient.getPricesByProductIdLastDays(productId, daysAmount);
        Mono<PriceDto> bestPriceMono =
                priceClient.getBestPriceByProductIdAndCity(productId, "almaty");

        return Mono.zip(productMono, pricesMono, bestPriceMono)
                .map(t -> {
                    ProductDto product = t.getT1();
                    List<String> categories = product.categories() == null
                            ? List.of()
                            : product.categories().stream().map(CategoryDto::title).toList();
                    return new ProductPriceViewWithCategory(product, categories, t.getT2(), t.getT3());
                });
    }

    public Mono<List<ProductDto>> postProducts(List<ProductDto> products) {
        return Mono.just(products);
    }
}
