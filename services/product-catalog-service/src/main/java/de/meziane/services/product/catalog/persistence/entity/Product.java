package de.meziane.services.product.catalog.persistence.entity;

import de.meziane.common.model.OrderStatus;
import de.meziane.common.model.ProductType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    @ToString.Include
    private ProductType productType ;

    @Column(name = "active", nullable = false)
    @ToString.Include
    private boolean active;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sku", nullable = false, unique = true, length = 64)
    @ToString.Include
    private String sku;

    @Column(name = "title", nullable = false, length = 250)
    @ToString.Include
    private String title;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    @ToString.Include
    private BigDecimal price;

    @Column(name = "currency", nullable = false, length = 3)
    @ToString.Include
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

    public Product(String title, BigDecimal price, String currency, String sku, ProductCategory category) {
        this.title = title;
        this.price = price;
        this.sku = sku;
        this.currency = currency;
        this.category = category;
    }

}
