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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class OrderItem extends BaseEntity {

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(Long productId, String sku, String title, int quantity, BigDecimal unitPrice) {
        validateProductId(productId);
        validateSku(sku);
        validateTitle(title);
        validateQuantity(quantity);
        validateUnitPrice(unitPrice);

        this.productId = productId;
        this.sku = sku;
        this.title = title;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    void assignTo(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order darf nicht null sein");
        }
        this.order = order;
    }

    void changeQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
    }

    BigDecimal lineTotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private void validateProductId(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("productId darf nicht null sein");
        }
    }

    private void validateSku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException("sku darf nicht leer sein");
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title darf nicht leer sein");
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity muss groesser als 0 sein");
        }
    }

    private void validateUnitPrice(BigDecimal unitPrice) {
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("unitPrice muss groesser als 0 sein");
        }
    }

}
