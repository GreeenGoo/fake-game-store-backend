package com.example.gameStore.controllers;

import com.example.gameStore.dtos.OrderDtos.ExtendedOrderDto;
import com.example.gameStore.dtos.OrderDtos.OrderDto;
import com.example.gameStore.services.interfaces.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @GetMapping("orders")
    public ResponseEntity<List<OrderDto>> findAllOrders() {
        List<OrderDto> orders = orderService.findAllOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("extended-orders")
    public ResponseEntity<List<ExtendedOrderDto>> findAllExtendedOrders() {
        List<ExtendedOrderDto> orders = orderService.findAllExtendedOrders();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable(required = true, name = "id") String id) {
        Optional<OrderDto> order = orderService.findOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("users/me/orders")
    public ResponseEntity<List<OrderDto>> findCurrentUserOrders() {
        List<OrderDto> orders = orderService.findOrdersByUser(UUID.randomUUID().toString());
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    @GetMapping("users/me/orders/{id}")
    public ResponseEntity<OrderDto> findCurrentUserOrderById(@PathVariable(required = true, name = "id") String id) {
        Optional<OrderDto> order = orderService.findOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("users/me/orders/current")
    public ResponseEntity<OrderDto> findCurrentUserCurrentOrder() {
        Optional<OrderDto> order = orderService.findCurrentOrderByUser(UUID.randomUUID());
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/user/{user_id}")
    public ResponseEntity<List<OrderDto>> findOrderByUserId(@PathVariable(required = true, name = "id") String id) {
        List<OrderDto> orders = orderService.findOrdersByUser(id);
        return orders.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orders);
    }

    @PostMapping("users/me/orders/current/game/{id}/{order_id}")
    public ResponseEntity<Void> addGameToOrder(@PathVariable(required = true, name = "id") String id,
                                               @PathVariable(required = true, name = "order_id") String orderId) {
        if (orderService.addGameToOrder(id, orderId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("users/me/orders/current/game/{id}")
    public ResponseEntity<OrderDto> deleteGameFromOrder(@PathVariable(required = true, name = "id") String id) {
        return orderService.deleteGameFromOrder(UUID.randomUUID(), UUID.randomUUID())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("users/me/orders/current")
    public ResponseEntity<OrderDto> cleanCurrentOrder() {
        return orderService.cleanCurrentOrder(UUID.randomUUID())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("users/me/orders/current/checkout")
    public ResponseEntity<OrderDto> checkoutCurrentOrder() {
        return orderService.checkoutCurrentOrder()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
