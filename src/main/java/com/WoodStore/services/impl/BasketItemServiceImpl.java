package com.WoodStore.services.impl;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.BasketItem;
import com.WoodStore.repositories.BasketItemRepository;
import com.WoodStore.services.BasketItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasketItemServiceImpl implements BasketItemService {
    private final BasketItemRepository basketItemRepository;

    @Autowired
    public BasketItemServiceImpl(BasketItemRepository basketItemRepository) {
        this.basketItemRepository = basketItemRepository;
    }


    @Override
    public void checkout(Basket basket) {
        this.basketItemRepository.saveAll(basket.getBasketItems());
    }
}
