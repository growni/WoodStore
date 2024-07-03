package com.WoodStore.controllers;


import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.BasketDto;
import com.WoodStore.services.BasketItemService;
import com.WoodStore.services.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/basket")
@SessionAttributes("basket")
public class BasketController {


    private final BasketService basketService;
    private final BasketItemService basketItemService;

    @Autowired
    public BasketController(BasketService basketService, BasketItemService basketItemService) {
        this.basketService = basketService;
        this.basketItemService = basketItemService;
    }

    @GetMapping
    public BasketDto getBasket() {
        return basketService.getBasket();
    }

    @PostMapping("/add")
    public void addItem(@RequestBody Product product, @RequestParam Integer quantity) {
        basketService.addItem(product, quantity);
    }

    @PostMapping("/remove")
    public void removeItem(@RequestBody Product product) {
        basketService.removeItem(product);
    }

    @PostMapping("/clear")
    public void clearBasket() {
        basketService.clearBasket();
    }

    @GetMapping("/total")
    public Double getTotalPrice() {
        return basketService.getTotalPrice();
    }

    @PostMapping("/checkout")
    public void checkout(@RequestBody Basket basket) {
        basketItemService.checkout(basket);
    }
}
