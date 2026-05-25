package de.meziane.services.order.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_order_items_order_sku",
                columnNames = {"order_id", "sku"}
        )
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(name = "product_id", nullable = false)
    @ToString.Include
    private Long productId;

    @Column(name = "sku", nullable = false, length = 64)
    @ToString.Include
    private String sku;

    @Column(name = "title", nullable = false, length = 250)
    @ToString.Include
    private String title;

    @Column(name = "quantity", nullable = false)
    @ToString.Include
    private int quantity;

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    @ToString.Include
    private BigDecimal unitPrice;

    @Column(name = "currency", nullable = false, length = 3)
    @ToString.Include
    private String currency;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    public OrderItem(
            Order order,
            Long productId,
            String sku,
            String title,
            int quantity,
            BigDecimal unitPrice,
            String currency
    ) {
        this.order = order;
        this.productId = productId;
        this.sku = sku;
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.currency = currency;
    }

}
