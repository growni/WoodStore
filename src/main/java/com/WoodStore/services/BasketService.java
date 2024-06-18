package com.WoodStore.services;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.BasketItem;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.BasketDto;

public interface BasketService {

    BasketDto getBasket();
    void addItem(Product product, Integer quantity);
    void removeItem(Product product);
    Double getTotalPrice();
    void clearBasket();
}
