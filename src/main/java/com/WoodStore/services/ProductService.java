package com.WoodStore.services;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.ProductNotFound;

import java.util.List;
import java.util.Set;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long productId) throws ProductNotFound;
    List<Product> findProductsCheaperThan(Double price) throws ProductNotFound;
    List<Product> findProductsMoreExpensiveThan(Double price) throws ProductNotFound;
    List<Product> findAvailableProducts();
    List<Product> filter(Double price, Set<ProductMaterial> materials, ProductCategory category);
    List<Product> findProductsByName(String name);
    List<Product> sortByPriceAsc();
    List<Product> sortByPriceDesc();
    List<Product> sortByFavorites();
    List<Product> sortBySizeAsc();
    List<Product> sortBySizeDesc();
    void deleteProductById(Long productId);
//    void updateDescription(Long productId, String description);
//    void updateName(Long productId, String name);
//    void updatePrice(Long productId, Double price);
//    void updateWidth(Long productId, Integer width);
//    void updateHeight(Long productId, Integer height);
//    void updateWeight(Long productId, Integer weight);
//    void updateAvailableQuantity(Long productId, Integer quantity);
    void addEmail(Long productId, String email);
    void removeEmail(Long productId, String email);
//    void addImage(Long productId, String imgUrl);
//    void updateImgUrl(Long productId, String imgUrl);
//    void updateMaterial(Long productId, ProductMaterial material);
//    void updateCategory(Long productId, ProductCategory category);
//    boolean removeImgUrl(Long productId, String imgUrl);
    void updateProduct(Product product);
}
