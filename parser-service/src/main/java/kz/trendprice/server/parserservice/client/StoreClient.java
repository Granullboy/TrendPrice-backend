package kz.trendprice.server.parserservice.client;

import kz.trendprice.server.parserservice.dto.stores.StoreDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class StoreClient {

    private final WebClient web;

    public StoreClient(WebClient.Builder builder) {
        this.web = builder
                .baseUrl("http://store-service")
                .build();
    }

    public Mono<List<StoreDto>> searchStores(String title) {
        return web.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/stores/search")
                        .queryParam("title", title)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}