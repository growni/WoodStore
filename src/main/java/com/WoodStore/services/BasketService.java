package com.WoodStore.services;

import com.WoodStore.entities.Basket;

public interface BasketService {
    Basket createBasket();
    Basket getBasket(Long basketId);
    void addProduct(Long basketId, Long productId);
    void increaseQuantity(Long basketId, Long productId);
    void decreaseQuantity(Long basketId, Long productId);
    void removeProduct(Long basketId, Long productId);
    void clear(Long basketId);
}
