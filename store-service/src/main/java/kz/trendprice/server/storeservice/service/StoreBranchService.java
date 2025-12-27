package kz.trendprice.server.storeservice.service;

import kz.trendprice.server.storeservice.entity.StoreBranche;
import kz.trendprice.server.storeservice.repository.StoreBrancheRepository;
import kz.trendprice.server.storeservice.specification.StoreBranchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoreBranchService {
    private final StoreBrancheRepository storeBrancheRepository;

    public List<StoreBranche> getAllStoreBranches() {
        return storeBrancheRepository.findAll();
    }

    public StoreBranche getStoreBrancheById(UUID id) {
        return storeBrancheRepository.getReferenceById(id);
    }

    public List<StoreBranche> filter(String title, String status, String openHours) {
        Specification<StoreBranche> specification = Specification
                .allOf(StoreBranchSpecification.hasStoreTitle(title))
                .and(StoreBranchSpecification.hasStatus(status))
                .and(StoreBranchSpecification.hasOpenHours(openHours));

        return storeBrancheRepository.findAll(specification);
    }

    public StoreBranche createStoreBranch(StoreBranche storeBranche) {
        return storeBrancheRepository.save(storeBranche);
    }

    public StoreBranche updateStoreBranch(UUID id, StoreBranche updatedStoreBranche) {
        return storeBrancheRepository.findById(id).map(storeBranche -> {
            if (updatedStoreBranche != null && !storeBranche.equals(updatedStoreBranche)) {
                if (updatedStoreBranche.getStore() != null) { storeBranche.setStore(updatedStoreBranche.getStore()); }
                if (updatedStoreBranche.getStatus() != null) { storeBranche.setStatus(updatedStoreBranche.getStatus()); }
                if (updatedStoreBranche.getOpenHours() != null) { storeBranche.setOpenHours(updatedStoreBranche.getOpenHours()); }
                if (updatedStoreBranche.getLatitude() != null) { storeBranche.setLatitude(updatedStoreBranche.getLatitude()); }
                if (updatedStoreBranche.getLongitude() != null) { storeBranche.setLongitude(updatedStoreBranche.getLongitude()); }
                storeBranche.setUpdatedAt(new Date());
                return storeBrancheRepository.save(storeBranche);
            } else return null;
        }).orElse(null);
    }

    public void deleteStoreBranch(UUID id) {
        storeBrancheRepository.deleteById(id);
    }
}
