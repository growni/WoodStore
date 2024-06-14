package com.WoodStore.services;

import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.ProductNotFound;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ProductNotFound;
    void deleteProductById(Long productId);
    List<Product> findProductsByName(String name);
    void updateDescription(Long productId, String description);
    void updateName(Long productId, String name);
    void updatePrice(Long productId, Double price);
    void updateWidth(Long productId, Integer width);
    void updateHeight(Long productId, Integer height);
    void updateWeight(Long productId, Integer weight);
    void updateAvailableQuantity(Long productId, Integer quantity);
    void addEmail(Long productId, String email);
    void removeEmail(Long productId, String email);
}
