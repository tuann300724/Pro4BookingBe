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
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Long id;
    
    @Column(name = "Name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "Description", columnDefinition = "NVARCHAR(MAX)")
    private String description;
    
    @Column(name = "Price", nullable = false, precision = 18, scale = 2)
    private BigDecimal price;
    
    @Column(name = "Stock", nullable = false)
    private Integer stock;
    
    @Column(name = "ImageURL", length = 255)
    private String imageUrl;
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "CategoryID")
    private Category category;
    
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails = new ArrayList<>();
    
    @OneToMany(mappedBy = "product")
    private List<CartItem> cartItems = new ArrayList<>();
}
