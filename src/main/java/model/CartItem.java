package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CartItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartItemID")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "ProductID")
    private Product product;
    
    @Column(name = "Quantity")
    private Integer quantity;
}
