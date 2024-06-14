package com.WoodStore.entities.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscribeRequest {
    private Long productId;
    private String email;
}
