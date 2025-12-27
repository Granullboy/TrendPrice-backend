package kz.trendprice.server.productservice.service;

import kz.trendprice.server.productservice.entity.Category;
import kz.trendprice.server.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategory() { return categoryRepository.findAll(); }

    public Category getCategoryById(UUID id) { return categoryRepository.findById(id).orElse(null); }

    public Category saveCategory(Category category) { return categoryRepository.save(category); }

    public Category updateCategory(UUID id, Category category) {
        return categoryRepository.findById(id).map(category1 -> categoryRepository.save(category1)).orElse(null);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
