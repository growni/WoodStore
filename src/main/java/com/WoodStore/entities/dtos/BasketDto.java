package com.WoodStore.entities.dtos;

import com.WoodStore.entities.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {
    private Long id;
    private Set<BasketItemDto> basketItems;
    private Double totalPrice;
    private String email;

    public BasketDto(Long id, Set<BasketItem> basketItems, String email) {
        this.id = id;
        this.basketItems = basketItems.stream().map(BasketItemDto::new).collect(Collectors.toSet());
        this.totalPrice = basketItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
        this.email = email;
    }
    public BasketDto(Long id, Set<BasketItem> basketItems) {
        this.id = id;
        this.basketItems = basketItems.stream().map(BasketItemDto::new).collect(Collectors.toSet());
        this.totalPrice = basketItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }
}
