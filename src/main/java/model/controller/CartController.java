package model.controller;

import lombok.RequiredArgsConstructor;
import model.CartItem;
import model.User;
import model.service.CartService;
import model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;
    private final UserService userService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItem>> getUserCart(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<CartItem> cartItems = cartService.getUserCart(user.get());
            return ResponseEntity.ok(cartItems);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/user/{userId}/add")
    public ResponseEntity<CartItem> addToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") Integer quantity) {
        
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            CartItem cartItem = cartService.addToCart(user.get(), productId, quantity);
            if (cartItem != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
            }
            return ResponseEntity.badRequest().build(); // Product not found or other issues
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestParam Integer quantity) {
        
        CartItem cartItem = cartService.updateCartItemQuantity(cartItemId, quantity);
        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            cartService.clearCart(user.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
