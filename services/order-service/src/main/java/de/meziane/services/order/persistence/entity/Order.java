package de.meziane.services.order.persistence.entity;


import de.meziane.common.model.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @Column(name = "order_id", nullable = false, unique = true, length = 100)
    @ToString.Include
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    @ToString.Include
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @ToString.Include
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    @ToString.Include
    private String currency;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public Order(
            String orderId,
            Long customerId,
            OrderStatus status,
            Instant createdAt,
            BigDecimal totalAmount,
            String currency
    ) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.currency = currency;
    }
}
