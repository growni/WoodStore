package com.WoodStore.services.impl;

import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.services.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
