package kz.trendprice.server.storeservice.service;

import kz.trendprice.server.storeservice.entity.StoreStatus;
import kz.trendprice.server.storeservice.repository.StoreStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreStatusService {
    private final StoreStatusRepository storeStatusRepository;

    public List<StoreStatus> getAllStoreStatuses() {
        return storeStatusRepository.findAll();
    }

    public StoreStatus getStoreStatusById(UUID id) {
        return storeStatusRepository.findById(id).orElse(null);
    }

    public List<StoreStatus> getStoreStatusByTitle(String title) {
        return storeStatusRepository.findByTitleContainingIgnoreCase(title);
    }

    public StoreStatus createStoreStatus(StoreStatus storeStatus) {
        return storeStatusRepository.save(storeStatus);
    }

    public StoreStatus updateStoreStatus(UUID id, StoreStatus updatedStoreStatus) {
        return storeStatusRepository.findById(id).map(storeStatus -> {
            if (updatedStoreStatus != null && !storeStatus.equals(updatedStoreStatus)) {
                if (updatedStoreStatus.getTitle() != null) {
                    storeStatus.setTitle(updatedStoreStatus.getTitle());
                }
                storeStatus.setUpdatedAt(new Date());
                return storeStatusRepository.save(storeStatus);
            } else return null;
        }).orElse(null);
    }

    public void deleteStoreStatus(UUID id) {
        storeStatusRepository.deleteById(id);
    }
}
