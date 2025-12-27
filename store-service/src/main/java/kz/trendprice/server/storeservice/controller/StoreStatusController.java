package kz.trendprice.server.storeservice.controller;

import kz.trendprice.server.storeservice.entity.StoreStatus;
import kz.trendprice.server.storeservice.service.StoreStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statuses")
public class StoreStatusController {
    private final StoreStatusService storeStatusService;

    @GetMapping
    public ResponseEntity<List<StoreStatus>> getStoreStatuses() {
        List<StoreStatus> statuses = storeStatusService.getAllStoreStatuses();
        return (statuses != null) ? ResponseEntity.ok(statuses) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreStatus> getStoreStatus(@PathVariable UUID id) {
        StoreStatus status = storeStatusService.getStoreStatusById(id);
        return (status != null) ? ResponseEntity.ok(status) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<StoreStatus>> searchStoreStatuses(String title) {
        List<StoreStatus> statuses = storeStatusService.getStoreStatusByTitle(title);
        return (statuses != null && !statuses.isEmpty()) ? ResponseEntity.ok(statuses) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StoreStatus> createStoreStatus(@RequestBody StoreStatus storeStatus) {
        StoreStatus status = storeStatusService.createStoreStatus(storeStatus);
        return (status != null) ? ResponseEntity.status(HttpStatus.CREATED).body(status) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreStatus> updateStoreStatus(@PathVariable UUID id, @RequestBody StoreStatus storeStatus) {
        try {
            StoreStatus storeStatus1 = storeStatusService.updateStoreStatus(id, storeStatus);
            return storeStatus1 != null ? ResponseEntity.ok(storeStatus1) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(storeStatus);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreStatus(@PathVariable UUID id) {
        try {
            storeStatusService.deleteStoreStatus(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
