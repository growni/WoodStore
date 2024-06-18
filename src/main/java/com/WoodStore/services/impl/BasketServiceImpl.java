package com.WoodStore.services.impl;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.BasketItem;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.BasketDto;
import com.WoodStore.repositories.BasketRepository;
import com.WoodStore.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private Basket basket;

    @Override
    public BasketDto getBasket() {
        return new BasketDto(basket.getId(), basket.getBasketItems());
    }

    public void addItem(Product product, Integer quantity) {
        basket.addItem(product, quantity);
    }

    public void removeItem(Product product) {
        basket.getBasketItems().removeIf(item -> item.getProduct().getId().equals(product.getId()));
    }

    public Double getTotalPrice() {
        return basket.getTotalPrice();
    }

    public void clearBasket() {
        basket.getBasketItems().clear();
    }
}
