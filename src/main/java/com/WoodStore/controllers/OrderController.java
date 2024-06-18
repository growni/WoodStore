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


}
