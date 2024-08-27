package com.WoodStore.services;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.dtos.BasketDto;

public interface BasketService {
    Basket createBasket(BasketDto basketDto);
    Basket getBasket(Long basketId);
    void addProduct(Long basketId, Long productId);
    void increaseQuantity(Long basketId, Long productId);
    void decreaseQuantity(Long basketId, Long productId);
    void removeProduct(Long basketId, Long productId);
    void clear(Long basketId);
}
