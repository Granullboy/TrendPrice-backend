package kz.trendprice.server.catalogbffservice.client;

import kz.trendprice.server.catalogbffservice.dto.stores.StoreDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class StoreClient {

    private WebClient web;
    private final String path = "http://store-service";

    public StoreClient(WebClient.Builder builder) { this.web = builder.build(); }

    public Mono<String> isAlive() {
        return web.get()
                .uri(path+"/status/isAlive")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<List<StoreDto>> getStores(String storeName) {
        return web.get()
                .uri(path+"/api")
                .retrieve()
                .bodyToFlux(StoreDto.class)
                .collectList();
    }
}
