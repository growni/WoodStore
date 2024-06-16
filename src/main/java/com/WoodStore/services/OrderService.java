package com.WoodStore.services;

import com.WoodStore.entities.Order;
import com.WoodStore.entities.Product;

import java.util.Set;

public interface OrderService {
    Order placeOrder(Set<Product> products);
}
