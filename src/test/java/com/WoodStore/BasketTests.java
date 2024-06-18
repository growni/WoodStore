package com.WoodStore;

import com.WoodStore.entities.BasketItem;
import com.WoodStore.entities.dtos.BasketDto;
import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Product;
import com.WoodStore.services.impl.BasketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BasketTests {
    @Mock
    private Basket basket;

    @InjectMocks
    private BasketServiceImpl basketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBasket_ShouldReturnBasketDto() {
        Set<BasketItem> basketItems = new HashSet<>();
        when(basket.getId()).thenReturn(1L);
        when(basket.getBasketItems()).thenReturn(basketItems);

        BasketDto result = basketService.getBasket();

        assertEquals(1L, result.getId());
        assertEquals(basketItems, result.getBasketItems());
        verify(basket, times(1)).getId();
        verify(basket, times(1)).getBasketItems();
    }

    @Test
    void addItem_ShouldAddItemToBasket() {
        Product product = new Product();
        product.setId(1L);
        int quantity = 2;

        basketService.addItem(product, quantity);

        verify(basket, times(1)).addItem(product, quantity);
    }

    @Test
    void removeItem_ShouldRemoveItemFromBasket() {
        Product product = new Product();
        product.setId(1L);
        Set<BasketItem> basketItems = new HashSet<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setProduct(product);
        basketItems.add(basketItem);
        when(basket.getBasketItems()).thenReturn(basketItems);

        basketService.removeItem(product);

        verify(basket, times(1)).getBasketItems();
        assertEquals(0, basketItems.size());
    }

    @Test
    void getTotalPrice_ShouldReturnTotalPrice() {
        double expectedTotalPrice = 100.0;
        when(basket.getTotalPrice()).thenReturn(expectedTotalPrice);

        double result = basketService.getTotalPrice();

        assertEquals(expectedTotalPrice, result);
        verify(basket, times(1)).getTotalPrice();
    }

    @Test
    void clearBasket_ShouldClearBasketItems() {
        Product product = new Product();
        product.setId(1L);

        basketService.addItem(product, 4);

        basketService.clearBasket();
        verify(basket, times(1)).getBasketItems().clear();
    }
}
