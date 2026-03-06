package kz.trendprice.server.productservice.controller;

import kz.trendprice.server.productservice.entity.Brand;
import kz.trendprice.server.productservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable UUID id) {
        Brand brand = brandService.getBrandById(id);
        return (brand == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(brand);
    }

    @PostMapping
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand createdBrand = brandService.saveBrand(brand);
        return (createdBrand == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(createdBrand);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable UUID id, @RequestBody Brand brand) {
        try {
            Brand brandUpdated = brandService.updateBrand(id, brand);
            return (brandUpdated == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(brandUpdated);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Brand> deleteBrand(@PathVariable UUID id) {
        try {
            brandService.deleteBrand(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

}
