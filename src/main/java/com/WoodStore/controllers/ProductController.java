package com.WoodStore.controllers;

import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.SubscribeRequest;
import com.WoodStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/search")
    public List<Product> findProductsByName(@RequestParam String name) {
        return this.productService.findProductsByName(name);
    }

    @PatchMapping("/name")
    public void updateProductName(@RequestBody Product product) {
        this.productService.updateName(product.getId(), product.getName());
    }

    @PatchMapping("/description")
    public void updateProductDescription(@RequestBody Product product) {
        this.productService.updateDescription(product.getId(), product.getDescription());
    }

    @PatchMapping("/price")
    public void updateProductPrice(@RequestBody Product product) {
        this.productService.updatePrice(product.getId(), product.getPrice());
    }

    @PatchMapping("/quantity")
    public void updateAvailableQuantity(@RequestBody Product product) {
        this.productService.updateAvailableQuantity(product.getId(), product.getQuantity());
    }

    @PatchMapping("/width")
    public void updateProductWidth(@RequestBody Product product) {
        this.productService.updateWidth(product.getId(), product.getWidth());
    }

    @PatchMapping("/height")
    public void updateProductHeight(@RequestBody Product product) {
        this.productService.updateWidth(product.getId(), product.getHeight());
    }

    @PatchMapping("/weight")
    public void updateProductWeight(@RequestBody Product product) {
        this.productService.updateWidth(product.getId(), product.getWeight());
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
