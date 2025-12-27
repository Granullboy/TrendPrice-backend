package kz.trendprice.server.storeservice.service;

import kz.trendprice.server.storeservice.entity.Store;
import kz.trendprice.server.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store getStoreById(UUID id) {
        return storeRepository.findById(id).orElse(null);
    }

    public List<Store> getStoresByTitle(String title) {
        return storeRepository.findByTitleContainingIgnoreCase(title);
    }

    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(UUID id, Store updatedStore) {
        return storeRepository.findById(id).map(store -> {
            if (updatedStore != null && !store.equals(updatedStore)) {
                if (updatedStore.getTitle() != null) { store.setTitle(updatedStore.getTitle()); }
                if (updatedStore.getDescription() != null) { store.setDescription(updatedStore.getDescription()); }
                if (updatedStore.getContactInfo() != null) { store.setContactInfo(updatedStore.getContactInfo()); }
                store.setUpdatedAt(new Date());
                return storeRepository.save(store);
            } else return null;
        }).orElse(null);
    }

    public void deleteStore(UUID id) {
        storeRepository.deleteById(id);
    }
}
