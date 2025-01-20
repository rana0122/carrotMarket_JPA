package miniproject.carrotmarket1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private String location;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(nullable = false)
    private String status;

    @Column(name = "used_yn", nullable = false)
    private String usedYn;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ProductImage> images;

    public void update(Product updatedProduct) {
        this.title = updatedProduct.getTitle();
        this.description = updatedProduct.getDescription();
        this.price = updatedProduct.getPrice();
        this.location = updatedProduct.getLocation();
        this.latitude = updatedProduct.getLatitude();
        this.longitude = updatedProduct.getLongitude();
        this.category = updatedProduct.getCategory();
        this.status = updatedProduct.getStatus();
        this.usedYn = updatedProduct.getUsedYn();
    }

}