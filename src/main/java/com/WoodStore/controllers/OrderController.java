package com.WoodStore.controllers;

import com.WoodStore.entities.*;
import com.WoodStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder() {
        return this.orderService.create();
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return this.orderService.getOrder(orderId);
    }

    @PutMapping("/{orderId}/addRecipient")
    public void addRecipient(@PathVariable Long orderId, @RequestBody Recipient recipient) {
        this.orderService.addRecipient(orderId, recipient);
    }

    @PutMapping("/{orderId}/addCarrier")
    public void addCarrier(@PathVariable Long orderId, @RequestBody Carrier carrier) {
        this.orderService.addCarrier(orderId, carrier);
    }

    @PutMapping("/{orderId}/addBasket")
    public void addBasket(@PathVariable Long orderId, @RequestParam Long basketId) {
        this.orderService.addBasket(orderId, basketId);
    }

    @PatchMapping("/{orderId}/place")
    public void placeOrder(@PathVariable Long orderId) {
        this.orderService.place(orderId);
    }
}
