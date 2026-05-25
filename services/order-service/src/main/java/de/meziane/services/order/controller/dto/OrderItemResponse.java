package de.meziane.services.order.controller.dto;


import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        Long productId,
        String sku,
        String title,
        int quantity,
        BigDecimal unitPrice,
        String currency
) {
}
