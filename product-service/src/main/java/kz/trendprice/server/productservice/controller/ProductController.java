package kz.trendprice.server.productservice.controller;

import kz.trendprice.server.productservice.entity.Product;
import kz.trendprice.server.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable UUID id) {
        Product product = productService.getProduct(id);
        return (product == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(product);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> filter(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String type
    ) {
        try {
            List<Product> products = productService.filter(title, category, brand, minPrice, maxPrice, type);
            return (products.isEmpty()) ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return (createdProduct == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(createdProduct);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Product> addCategory(@PathVariable UUID id, @RequestBody List<UUID> uuids) {
        Product product = productService.addCategory(id, uuids);
        return (product == null) ? ResponseEntity.noContent().build() : ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        try {
            Product product1 = productService.updateProduct(id, product);
            return product1 != null ? ResponseEntity.ok(product1) : ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(product);
        }    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
