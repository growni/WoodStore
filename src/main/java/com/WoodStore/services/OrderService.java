package com.WoodStore.services;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.OrderProduct;

import java.util.Set;

public interface OrderService {
    Order createOrder(Set<Product> products);
    void placeOrder(Long orderId);
    Order getOrderById(Long orderId);
    Set<Order> getAllOrders();
    void updateStatus(Long orderId, OrderStatus status);
}
