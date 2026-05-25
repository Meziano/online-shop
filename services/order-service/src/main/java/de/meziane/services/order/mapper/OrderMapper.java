package de.meziane.services.order.mapper;

import de.meziane.services.order.controller.dto.OrderItemRequest;
import de.meziane.services.order.controller.dto.OrderItemResponse;
import de.meziane.services.order.controller.dto.OrderRequest;
import de.meziane.services.order.controller.dto.OrderResponse;
import de.meziane.services.order.persistence.entity.Order;
import de.meziane.services.order.persistence.entity.OrderItem;

import java.util.List;

public final class OrderMapper {

    public static OrderResponse toResponse(Order order) {
        OrderResponse orderResponse =
                new OrderResponse(
                        order.getId(),
                        order.getOrderId(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getTotalAmount(),
                        order.getCurrency(),
                        order.getItems().stream()
                                .map(OrderMapper::toOrderItemResponse)
                                .toList());
        return orderResponse;
    }

    public static Order toOrder(OrderRequest orderRequest) {

        Order order = new Order(
                orderRequest.orderId(),
                orderRequest.customerId(),
                orderRequest.status(),
                orderRequest.createdAt(),
                orderRequest.totalAmount(),
                orderRequest.currency()
        );
        orderRequest.items().forEach(itemRequest ->
                order.addItem(toOrderItem(itemRequest, order))
        );
        return order;
    }
    private static OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProductId(),
                orderItem.getSku(),
                orderItem.getTitle(),
                orderItem.getQuantity(),
                orderItem.getUnitPrice(),
                orderItem.getCurrency()
        );
    }
    private static OrderItem toOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return new OrderItem(
                order,
                orderItemRequest.productId(),
                orderItemRequest.sku(),
                orderItemRequest.title(),
                orderItemRequest.quantity(),
                orderItemRequest.unitPrice(),
                orderItemRequest.currency()
        );
    }

    
}
