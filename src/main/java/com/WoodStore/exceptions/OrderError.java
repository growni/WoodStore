package com.WoodStore.exceptions;

public class OrderError extends RuntimeException {
    public OrderError(String message) {
        super(message);
    }
}
