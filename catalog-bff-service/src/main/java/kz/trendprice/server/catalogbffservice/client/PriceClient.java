package kz.trendprice.server.catalogbffservice.client;

import kz.trendprice.server.catalogbffservice.dto.prices.PriceDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PriceClient {

    private WebClient web;
    private final String path = "http://price-service";

    public PriceClient(WebClient.Builder builder) {
        this.web = builder.build();
    }

    public Mono<String> isAlive() {
        return web.get()
                .uri(path+"/status/isAlive")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<List<PriceDto>> getPricesByProductIdLastDays(String productId, Integer days_amount) {
        return web.get()
                .uri(path + "/api/prices/product/{productId}/days/{days_amount}", productId, days_amount)
                .retrieve()
                .bodyToFlux(PriceDto.class)
                .collectList();
    }

    public Mono<PriceDto> getBestPriceByProductIdAndCity(String productId, String city) {
        return web.get()
                .uri(path + "/api/prices/best/{productId}/{city}", productId, city)
                .retrieve()
                .bodyToMono(PriceDto.class);
    }

    public Mono<PriceDto> postPrice(PriceDto price) {
        return web.post()
                .uri(path+"/api/prices")
                .bodyValue(price)
                .retrieve()
                .bodyToMono(PriceDto.class);
    }
}
