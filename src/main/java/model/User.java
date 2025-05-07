package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long id;
    
    @Column(name = "Username", unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(name = "PasswordHash", nullable = false)
    private String passwordHash;
    
    @Column(name = "Email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "FullName", length = 100)
    private String fullName;
    
    @Column(name = "Phone", length = 20)
    private String phone;
    
    @Column(name = "Address", columnDefinition = "NVARCHAR(MAX)")
    private String address;
    
    @Column(name = "Role", length = 20)
    private String role = "customer";
    
    @Column(name = "CreatedAt")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();
}
