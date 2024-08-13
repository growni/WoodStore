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
