package kz.trendprice.server.parserservice.client;

import kz.trendprice.server.parserservice.dto.prices.PriceDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PriceClient {

    private final WebClient web;

    public PriceClient(WebClient.Builder builder) {
        this.web = builder
                .baseUrl("http://price-service")
                .build();
    }

    public Mono<List<PriceDto>> massCreate(List<PriceDto> prices) {
        return web.post()
                .uri("/api/prices/massCreate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(prices)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}