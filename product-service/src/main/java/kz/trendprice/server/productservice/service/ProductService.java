package kz.trendprice.server.productservice.service;

import kz.trendprice.server.productservice.entity.Category;
import kz.trendprice.server.productservice.entity.Product;
import kz.trendprice.server.productservice.repository.CategoryRepository;
import kz.trendprice.server.productservice.repository.ProductRepository;
import kz.trendprice.server.productservice.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(UUID id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> filter(String title, String category, String brand, String type ) {
        Specification<Product> specification = Specification
                .allOf(ProductSpecification.hasTitle(title))
                .and(ProductSpecification.hasCategory(category))
                .and(ProductSpecification.hasBrand(brand))
                .and(ProductSpecification.hasType(type));

        return productRepository.findAll(specification);
    }

    public Product addCategory(UUID id, List<UUID> uuids) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;

        List<Category> categories = product.getCategories();
        if(categories == null) categories = new ArrayList<>();
        for (UUID uuid : uuids) {
            Category category = categoryRepository.findById(uuid).orElse(null);
            if (category != null) categories.add(category);
        }
        product.setCategories(categories);
        return productRepository.save(product);
    }

    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }



    public Product updateProduct(UUID id, Product product) {
        Product old = productRepository.findById(id).orElse(null);
        if (old == null) return null;

        if (product.getTitle() != null) old.setTitle(product.getTitle());
        if (product.getBrand() != null) old.setBrand(product.getBrand());
        if (product.getType() != null) old.setType(product.getType());
        if (product.getBarcode() != null) old.setBarcode(product.getBarcode());
        if (product.getCategories() != null) old.setCategories(product.getCategories());

        return productRepository.save(old);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
