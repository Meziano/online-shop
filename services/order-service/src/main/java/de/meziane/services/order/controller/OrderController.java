package de.meziane.services.order.controller;

import de.meziane.services.order.controller.dto.OrderRequest;
import de.meziane.services.order.controller.dto.OrderResponse;
import de.meziane.services.order.mapper.OrderMapper;
import de.meziane.services.order.persistence.entity.Order;
import de.meziane.services.order.persistence.repository.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    
    @Autowired
    private final OrderRepository orderRepository;

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable long id) {
        Order order = orderRepository.findWithItemsById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return OrderMapper.toResponse(order);
    }

    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        Order order = OrderMapper.toOrder(orderRequest);
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toResponse(savedOrder);
    }

}
