package com.WoodStore.controllers;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.SubscribeRequest;
import com.WoodStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return this.productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return this.productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        this.productService.deleteProductById(id);
    }

    @GetMapping("/filter/name")
    public List<Product> filterProductsByName(@RequestParam String name) {
        return this.productService.findProductsByName(name);
    }

    @GetMapping("/filter/available")
    public List<Product> filterAvailableProducts() {
        return this.productService.findAvailableProducts();
    }

    @GetMapping("/filter")
    public List<Product> filter(
            @RequestParam(required = false)Double price,
            @RequestParam(required = false)Set<ProductMaterial> materials,
            @RequestParam(required = false)ProductCategory category) {
        return productService.filter(price, materials, category);
    }

    @PatchMapping("/update/name")
    public void updateProductName(@RequestBody Product product) {
        this.productService.updateName(product.getId(), product.getName());
    }

    @PatchMapping("/update/description")
    public void updateProductDescription(@RequestBody Product product) {
        this.productService.updateDescription(product.getId(), product.getDescription());
    }

    @PatchMapping("/update/price")
    public void updateProductPrice(@RequestBody Product product) {
        this.productService.updatePrice(product.getId(), product.getPrice());
    }

    @PatchMapping("/update/quantity")
    public void updateAvailableQuantity(@RequestBody Product product) {
        this.productService.updateAvailableQuantity(product.getId(), product.getQuantity());
    }

    @PatchMapping("/update/width")
    public void updateProductWidth(@RequestBody Product product) {
        this.productService.updateWidth(product.getId(), product.getWidth());
    }

    @PatchMapping("/update/height")
    public void updateProductHeight(@RequestBody Product product) {
        this.productService.updateHeight(product.getId(), product.getHeight());
    }

    @PatchMapping("/update/weight")
    public void updateProductWeight(@RequestBody Product product) {
        this.productService.updateWeight(product.getId(), product.getWeight());
    }

    @PatchMapping("/update/image")
    public void updateImgUrl(@RequestBody Product product) {
        this.productService.updateImgUrl(product.getId(), product.getImageUrl());
    }

    @PatchMapping("/update/image/add")
    public void addImage(@RequestBody Product product) {
        this.productService.addImage(product.getId(), product.getImageUrl());
    }

    @DeleteMapping("/update/image/remove")
    public boolean removeImage(@RequestBody Product product) {
        return this.productService.removeImgUrl(product.getId(), product.getImageUrl());
    }

    @GetMapping("/sort/price/asc")
    public List<Product> sortByPriceAsc() {
        return this.productService.sortByPriceAsc();
    }

    @GetMapping("/sort/price/desc")
    public List<Product> sortByPriceDesc() {
        return this.productService.sortByPriceDesc();
    }

    @GetMapping("/sort/favorites")
    public List<Product> sortByFavorites() {
        return this.productService.sortByFavorites();
    }

    @GetMapping("/sort/size/asc")
    public List<Product> sortBySizeAsc() {
        return this.productService.sortBySizeAsc();
    }

    @GetMapping("/sort/size/desc")
    public List<Product> sortBySizeDesc() {
        return this.productService.sortBySizeDesc();
    }

    @PatchMapping("/subscribe")
    public void subscribeForProduct(@RequestBody SubscribeRequest request) {
        this.productService.addEmail(request.getProductId(), request.getEmail());
    }

    @PatchMapping("/unsubscribe")
    public void unsubscribeForProduct(@RequestBody SubscribeRequest request) {
        this.productService.removeEmail(request.getProductId(), request.getEmail());
    }

}
