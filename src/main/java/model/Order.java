package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    
    @Column(name = "OrderDate")
    private LocalDateTime orderDate = LocalDateTime.now();
    
    @Column(name = "TotalAmount", precision = 18, scale = 2)
    private BigDecimal totalAmount;
    
    @Column(name = "Status", length = 20)
    private String status = "pending";
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
