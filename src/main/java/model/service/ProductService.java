package model.service;

import lombok.RequiredArgsConstructor;
import model.Category;
import model.Product;
import model.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }
    
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    public boolean updateStock(Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int newStock = product.getStock() - quantity;
            if (newStock >= 0) {
                product.setStock(newStock);
                productRepository.save(product);
                return true;
            }
        }
        return false;
    }
}
