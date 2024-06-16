package com.WoodStore.services.impl;

import com.WoodStore.entities.Product;
import com.WoodStore.entities.Order;
import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(Set<Product> products) {
        Order order = new Order();

        order.setProducts(products);
        return this.orderRepository.save(order);
    }
}
