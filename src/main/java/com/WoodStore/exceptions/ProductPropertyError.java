package com.WoodStore.exceptions;

public class ProductPropertyError extends RuntimeException{
    public ProductPropertyError(String message) {
        super(message);
    }
}
