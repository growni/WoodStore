package com.WoodStore.services.impl;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.BasketDto;
import com.WoodStore.repositories.BasketRepository;
import com.WoodStore.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService {

    private Basket basket;
    private final BasketRepository basketRepository;


    @Autowired
    public BasketServiceImpl(Basket basket, BasketRepository basketRepository) {
        this.basket = basket;
        this.basketRepository = basketRepository;
    }

    @Override
    public BasketDto getBasket() {
        return new BasketDto(basket.getId(), basket.getBasketItems(), basket.getEmail());
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

    @Override
    public void checkout(Basket basket) {
        basket.setTotalPrice();
        this.basketRepository.save(basket);
    }
}
