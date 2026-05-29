package de.meziane.services.order.controller.dto;

import de.meziane.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        Long id,
        String orderId,
        Long customerId,
        OrderStatus status,
        Instant createdAt,
        BigDecimal totalAmount,
        String currency,
        List<OrderItemResponse> items) {
}
