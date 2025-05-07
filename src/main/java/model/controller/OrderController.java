package model.controller;

import lombok.RequiredArgsConstructor;
import model.Order;
import model.OrderDetail;
import model.User;
import model.service.OrderService;
import model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;
    private final UserService userService;
    
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            List<Order> orders = orderService.getOrdersByUser(user.get());
            return ResponseEntity.ok(orders);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping("/user/{userId}/checkout")
    public ResponseEntity<Order> createOrderFromCart(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            Order order = orderService.createOrderFromCart(user.get());
            if (order != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(order);
            }
            return ResponseEntity.badRequest().build(); // Empty cart or other issues
        }
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        Order updatedOrder = orderService.updateOrderStatus(id, status);
        if (updatedOrder != null) {
            return ResponseEntity.ok(updatedOrder);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{id}/details")
    public ResponseEntity<List<OrderDetail>> getOrderDetails(@PathVariable Long id) {
        List<OrderDetail> orderDetails = orderService.getOrderDetails(id);
        if (orderDetails != null) {
            return ResponseEntity.ok(orderDetails);
        }
        return ResponseEntity.notFound().build();
    }
}
