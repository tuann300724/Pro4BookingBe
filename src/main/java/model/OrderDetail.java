package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderDetails")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetailID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "OrderID")
    private Order order;
    
    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;
    
    @Column(name = "Quantity")
    private Integer quantity;
    
    @Column(name = "UnitPrice", precision = 18, scale = 2)
    private BigDecimal unitPrice;
}
