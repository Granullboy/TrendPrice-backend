package kz.trendprice.server.storeservice.controller;
import kz.trendprice.server.storeservice.entity.StoreBranche;
import kz.trendprice.server.storeservice.service.StoreBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store-branches")
public class StoreBrancheController {
    private final StoreBranchService storeBranchService;

    @GetMapping
    public ResponseEntity<List<StoreBranche>> getAllStoreBranches() {
        List<StoreBranche> storeBranches = storeBranchService.getAllStoreBranches();
        return (storeBranches != null) ? ResponseEntity.ok(storeBranches) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreBranche> getStoreBranche(@PathVariable UUID id) {
        StoreBranche storeBranche = storeBranchService.getStoreBrancheById(id);
        return storeBranche != null ? ResponseEntity.ok(storeBranche) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<StoreBranche>> filter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String openHours
            ) {
        try {
            List<StoreBranche> storeBranches = storeBranchService.filter(title, status, openHours);
            return (storeBranches != null && !storeBranches.isEmpty()) ? ResponseEntity.ok(storeBranches) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<StoreBranche> createStoreBranche(@RequestBody StoreBranche storeBranche) {
        StoreBranche store = storeBranchService.createStoreBranch(storeBranche);
        return store != null ? ResponseEntity.status(HttpStatus.CREATED).body(store) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<StoreBranche> updateStoreBranche(@PathVariable UUID id, @RequestBody StoreBranche storeBranche) {
        try {
            StoreBranche store = storeBranchService.updateStoreBranch(id, storeBranche);
            return store != null ? ResponseEntity.ok(store) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(storeBranche);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStoreBranche(@PathVariable UUID id) {
        try {
            storeBranchService.deleteStoreBranch(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
