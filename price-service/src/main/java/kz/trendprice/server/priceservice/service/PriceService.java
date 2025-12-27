package kz.trendprice.server.priceservice.service;

import kz.trendprice.server.priceservice.entity.Price;
import kz.trendprice.server.priceservice.repository.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceService {
    private final PriceRepository priceRepository;

    public List<Price> getPrices() {
        return priceRepository.findAllByOrderByTimeDesc();
    }

    public Price getPrice(UUID priceId) {
        return priceRepository.findById(priceId).orElse(null);
    }

    public List<Price> getPricesByStoreId(UUID store_id) {
        return priceRepository.findAllByStoreIdOrderByTimeDesc(store_id);
    }

    public List<Price> getPricesByProductId(UUID product_id) {
        return priceRepository.findAllByProductIdOrderByTimeDesc(product_id);
    }

    public List<Price> getPricesByStoreIdAndProductId(UUID store_id, UUID product_id) {
        return priceRepository.findAllByProductIdAndStoreIdOrderByTimeDesc(product_id, store_id);
    }

    public Price createPrice(Price price) {
        return priceRepository.save(price);
    }

    public Price updatePrice(UUID priceId, Price price) {
        Price oldPrice = priceRepository.findById(priceId).orElse(null);
        if(oldPrice == null) return null;

        if (price.getProductId() != null) oldPrice.setProductId(price.getProductId());
        if (price.getStoreId() != null) oldPrice.setStoreId(price.getStoreId());
        if (price.getUnitAmount() != null) oldPrice.setUnitAmount(price.getUnitAmount());
        if (price.getPricePerUnit() != null) oldPrice.setUnit(price.getUnit());
        if (price.getUnit() != null) oldPrice.setUnit(price.getUnit());
        if (price.getCurrency() != null) oldPrice.setCurrency(price.getCurrency());


        return priceRepository.save(oldPrice);
    }

    public void deletePrice(UUID priceId) {
        priceRepository.deleteById(priceId);
    }
}
