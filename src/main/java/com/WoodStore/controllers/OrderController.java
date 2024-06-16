package com.WoodStore.controllers;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.Product;
import com.WoodStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public void placeOrder(@RequestBody Set<Product> products) {
        Order order = this.orderService.createOrder(products);
        this.orderService.placeOrder(order.getId());
    }

    @GetMapping
    public Set<Order> getAllOrders() {
        return this.orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(Long orderId) {
        return this.orderService.getOrderById(orderId);
    }

    @PatchMapping("/update/status")
    public void updateOrderStatus(@RequestBody Order order) {
        this.orderService.updateStatus(order.getId(), order.getStatus());
    }
}
