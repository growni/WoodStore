package com.WoodStore.entities.dtos;

import lombok.Getter;

import java.util.List;

@Getter
public class BasketDto {
    private List<BasketItemDto> items;
}
