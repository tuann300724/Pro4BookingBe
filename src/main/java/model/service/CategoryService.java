package model.service;

import lombok.RequiredArgsConstructor;
import model.Category;
import model.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
    
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
