package kz.trendprice.server.productservice.controller;

import kz.trendprice.server.productservice.entity.Category;
import kz.trendprice.server.productservice.entity.Product;
import kz.trendprice.server.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategory();
        return (categories.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return (category == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(category);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable UUID id) {
        Category category = categoryService.getCategoryById(id);
        return (category == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(category.getProducts());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category category1 = categoryService.saveCategory(category);
        return (category1 == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(category1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable UUID id, @RequestBody Category category) {
        Category category1 = categoryService.updateCategory(id, category);
        return (category1 == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(category1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return (id == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok().build();
    }
}
