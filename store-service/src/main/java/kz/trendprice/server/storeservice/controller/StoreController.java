package kz.trendprice.server.storeservice.controller;
import kz.trendprice.server.storeservice.entity.Store;
import kz.trendprice.server.storeservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        return (stores != null) ? ResponseEntity.ok(stores) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Store> getStore(@PathVariable UUID id) {
        Store store = storeService.getStoreById(id);
        return store != null ? ResponseEntity.ok(store) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Store>> searchStores(@RequestParam String title) {
        List<Store> stores = storeService.getStoresByTitle(title);
        return (stores != null && !stores.isEmpty()) ? ResponseEntity.ok(stores) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Store> createStore(@RequestBody Store store) {
        Store storeCreated = storeService.createStore(store);
        return storeCreated != null ? ResponseEntity.ok(storeCreated) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable UUID id, @RequestBody Store store) {
        try {
            Store store1 = storeService.updateStore(id, store);
            return store1 != null ? ResponseEntity.ok(store1) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(store);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable UUID id) {
        try {
            storeService.deleteStore(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
