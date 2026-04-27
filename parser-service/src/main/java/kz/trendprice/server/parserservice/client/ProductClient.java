package kz.trendprice.server.parserservice.client;

import kz.trendprice.server.parserservice.dto.products.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductClient {

    private final WebClient web;

    public ProductClient(WebClient.Builder builder) {
        this.web = builder
                .baseUrl("http://product-service")
                .build();
    }

    public Mono<ProductDto> getProduct(String productId) {
        return web.get()
                .uri("/api/products/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> createProduct(ProductDto product) {
        return web.post()
                .uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<List<ProductDto>> massCreate(List<ProductDto> products) {
        return web.post()
                .uri("/api/products/massCreate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(products)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}