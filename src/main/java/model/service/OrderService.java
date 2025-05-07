package model.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.*;
import model.repository.CartItemRepository;
import model.repository.OrderDetailRepository;
import model.repository.OrderRepository;
import model.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }
    
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
    
    @Transactional
    public Order createOrderFromCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            return null;
        }
        
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("pending");
        
        Order savedOrder = orderRepository.save(order);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            if (productService.updateStock(product.getId(), cartItem.getQuantity())) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(savedOrder);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setUnitPrice(product.getPrice());
                orderDetailRepository.save(orderDetail);
                
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                totalAmount = totalAmount.add(itemTotal);
            }
        }
        
        savedOrder.setTotalAmount(totalAmount);
        orderRepository.save(savedOrder);
        
        // Clear the cart
        cartItemRepository.deleteByUser(user);
        
        return savedOrder;
    }
    
    public Order updateOrderStatus(Long orderId, String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            return orderRepository.save(order);
        }
        return null;
    }
    
    public List<OrderDetail> getOrderDetails(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        return optionalOrder.map(orderDetailRepository::findByOrder).orElse(null);
    }
}
