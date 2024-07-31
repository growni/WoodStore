package com.WoodStore.controllers;

import com.WoodStore.entities.Basket;
import com.WoodStore.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baskets")
public class BasketController {

    private final BasketService basketService;

    @Autowired
    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public Basket createBasket() {
        return this.basketService.createBasket();
    }

    @GetMapping("/{basketId}")
    public Basket getBasket(@PathVariable Long basketId) {
        return this.basketService.getBasket(basketId);
    }

    @PostMapping("/{basketId}/products/{productId}")
    public void addProduct(@PathVariable Long basketId, @PathVariable Long productId) {
        this.basketService.addProduct(basketId, productId);
    }

    @PatchMapping("/{basketId}/products/{productId}")
    public void adjustQuantity(@PathVariable Long basketId, @PathVariable Long productId, @RequestParam boolean increment) {
        if(increment) {
            this.basketService.increaseQuantity(basketId, productId);
        } else {
            this.basketService.decreaseQuantity(basketId, productId);
        }
    }

    @DeleteMapping("/{basketId}/products/{productId}")
    public void removeProduct(@PathVariable Long basketId, @PathVariable Long productId) {
        this.basketService.removeProduct(basketId, productId);
    }

    @DeleteMapping("/{basketId}/clear")
    public void clearBasket(@PathVariable Long basketId) {
        this.basketService.clear(basketId);
    }
}
