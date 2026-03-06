package kz.trendprice.server.productservice.service;

import kz.trendprice.server.productservice.entity.Brand;
import kz.trendprice.server.productservice.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(UUID id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public Brand updateBrand(UUID id, Brand brand) {
        Brand old = brandRepository.findById(id).orElse(null);
        if (old == null) return null;

        if(brand.getTitle() != null) old.setTitle(brand.getTitle());
        if(brand.getDescription() != null) old.setDescription(brand.getDescription());

        return brandRepository.save(old);
    }

    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }
}
