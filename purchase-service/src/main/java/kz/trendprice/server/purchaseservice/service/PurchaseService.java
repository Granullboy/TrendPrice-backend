package kz.trendprice.server.purchaseservice.service;

import kz.trendprice.server.purchaseservice.entity.Purchase;
import kz.trendprice.server.purchaseservice.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public List<Purchase> getAllPurchases() { return purchaseRepository.findAll(); }

    public Purchase getPurchase(UUID purchaseId) { return purchaseRepository.findById(purchaseId).orElse(null); }

    public Purchase createPurchase(Purchase purchase) { return purchaseRepository.save(purchase); }

    public Purchase updatePurchase(UUID purchaseId, Purchase purchase) {
        Purchase oldPurchase = purchaseRepository.findById(purchaseId).orElse(null);
        if (oldPurchase == null) return null;

        return purchaseRepository.save(purchase);
    }

    public void deletePurchase(UUID purchaseId) { purchaseRepository.deleteById(purchaseId); }
}
