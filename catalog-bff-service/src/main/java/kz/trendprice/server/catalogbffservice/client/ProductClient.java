package kz.trendprice.server.catalogbffservice.client;

import kz.trendprice.server.catalogbffservice.dto.products.ProductDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private WebClient web;
    private final String path = "http://product-service/";

    public ProductClient(WebClient.Builder builder) {
        this.web = builder.build();
    }

    public Mono<String> isAlive() {
        return web.get()
                .uri(path+"/status/isAlive")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<ProductDto> getProduct(String productId) {
        return web.get()
                .uri(path + "/api/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
