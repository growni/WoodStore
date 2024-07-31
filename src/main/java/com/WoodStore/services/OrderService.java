package com.WoodStore.services;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Carrier;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.Recipient;

public interface OrderService {
    Order create();
    Order getOrder(Long orderId);
    void addRecipient(Long orderId, Recipient recipient);
    void addCarrier(Long orderId, Carrier carrier);
    void addBasket(Long orderId, Long basketId);
    void place(Long orderId);
}
