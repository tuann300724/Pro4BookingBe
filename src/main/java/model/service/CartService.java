package model.service;

import lombok.RequiredArgsConstructor;
import model.CartItem;
import model.Product;
import model.User;
import model.repository.CartItemRepository;
import model.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    
    public List<CartItem> getUserCart(User user) {
        return cartItemRepository.findByUser(user);
    }
    
    public CartItem addToCart(User user, Long productId, Integer quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            
            // Check if product is already in cart
            Optional<CartItem> existingItem = cartItemRepository.findByUserAndProduct(user, product);
            
            if (existingItem.isPresent()) {
                // Update existing cart item
                CartItem cartItem = existingItem.get();
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                return cartItemRepository.save(cartItem);
            } else {
                // Create new cart item
                CartItem cartItem = new CartItem();
                cartItem.setUser(user);
                cartItem.setProduct(product);
                cartItem.setQuantity(quantity);
                return cartItemRepository.save(cartItem);
            }
        }
        return null;
    }
    
    public CartItem updateCartItemQuantity(Long cartItemId, Integer quantity) {
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
        if (optionalCartItem.isPresent() && quantity > 0) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
            return cartItemRepository.save(cartItem);
        }
        return null;
    }
    
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    
    public void clearCart(User user) {
        cartItemRepository.deleteByUser(user);
    }
}
