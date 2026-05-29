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
public class Order extends BaseEntity {

    @Column(name = "order_id", nullable = false, unique = true, length = 100)
    @ToString.Include
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    @ToString.Include
    private Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    @ToString.Include
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    @ToString.Include
    private String currency;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderId, Long customerId, String currency) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId darf nicht leer sein");
        }
        if (customerId == null) {
            throw new IllegalArgumentException("customerId darf nicht null sein");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency darf nicht leer sein");
        }

        this.orderId = orderId;
        this.customerId = customerId;
        this.currency = currency;
        this.status = OrderStatus.CREATED;
        this.totalAmount = BigDecimal.ZERO;
    }

    public void addItem(Long productId, String sku, String title, int quantity, BigDecimal unitPrice) {
        ensureEditable();

        OrderItem item = new OrderItem(productId, sku, title, quantity, unitPrice);
        item.assignTo(this);
        items.add(item);
        recalculateTotalAmount();
    }

    public void removeItem(Long orderItemId) {
        ensureEditable();

        boolean removed = items.removeIf(item -> item.getId().equals(orderItemId));
        if (!removed) {
            throw new IllegalArgumentException("OrderItem nicht gefunden");
        }
        recalculateTotalAmount();
    }

    public void changeItemQuantity(Long orderItemId, int quantity) {
        ensureEditable();

        OrderItem item = items.stream()
                .filter(it -> it.getId().equals(orderItemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("OrderItem nicht gefunden"));

        item.changeQuantity(quantity);
        recalculateTotalAmount();
    }

    public void pay() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Nur CREATED Orders koennen bezahlt werden");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Order muss mindestens ein Item enthalten");
        }
        this.status = OrderStatus.PAID;
    }

    public void confirm() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Nur PAID Orders koennen bestaetigt werden");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Nur CREATED Orders koennen storniert werden");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    private void ensureEditable() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Items duerfen nur im Status CREATED geaendert werden");
        }
    }

    private void recalculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(OrderItem::lineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
