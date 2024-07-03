package com.WoodStore.services;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.Product;

import java.util.Set;

public interface OrderService {
    void placeOrder(Basket basket);
}
