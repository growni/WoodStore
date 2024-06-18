package com.WoodStore.entities.dtos;

import com.WoodStore.entities.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemDto {
    private Long id;
    private ProductDto product;
    private Integer quantity;

    public BasketItemDto(BasketItem basketItem) {
        this.id = basketItem.getId();
        this.product = new ProductDto(basketItem.getProduct());
        this.quantity = basketItem.getQuantity();
    }
}
