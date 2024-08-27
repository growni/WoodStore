package com.WoodStore.services.impl;

import com.WoodStore.configuration.ProductSpecification;
import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.*;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.WoodStore.messages.errors.ProductErrors.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        product.validate();

        this.productRepository.save(product);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(Long productId) {
        return this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));
    }

    @Override
    public List<Product> findProductsCheaperThan(Double price) {
        List<Product> products = this.productRepository.findAllByPriceLessThan(price);

        if(products.size() == 0) {
            throw new ProductNotFound(String.format(CHEAPER_PRODUCTS_NOT_FOUND_ERROR, price));
        }

        return products;
    }

    @Override
    public List<Product> findProductsMoreExpensiveThan(Double price) {
        List<Product> products = this.productRepository.findAllByPriceGreaterThan(price);

        if(products.size() == 0) {
            throw new ProductNotFound(String.format(EXPENSIVE_PRODUCTS_NOT_FOUND_ERROR, price));
        }

        return products;
    }

    @Override
    public List<Product> findAvailableProducts() {
        List<Product> products = this.productRepository.findAvailableProducts();

        if(products.size() == 0) {
            throw new ProductNotFound(AVAILABLE_PRODUCTS_NOT_FOUND_ERROR);
        }

        return products;
    }

    @Override
    public List<Product> filter(Double price, Set<ProductMaterial> materials, ProductCategory category) {
        return productRepository.findAll(new ProductSpecification(price, materials, category));
    }

    @Override
    public void deleteProductById(Long productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    public List<Product> findProductsByName(String productName) {
        return this.productRepository.findProductsByName(productName);
    }

    @Override
    public List<Product> sortByPriceAsc() {
        List<Product> products = this.productRepository.sortByPriceAsc();

        if(products == null || products.isEmpty()) {
            return null;
        }

        return products;
    }

    @Override
    public List<Product> sortByPriceDesc() {
        List<Product> products = this.productRepository.sortByPriceDesc();

        if(products == null || products.isEmpty()) {
            return null;
        }

        return products;
    }

    @Override
    public List<Product> sortByFavorites() {
        List<Product> products = this.productRepository.findAllSubscribedProducts();

        if(products == null || products.isEmpty()) {
            return null;
        }

        return products.stream().sorted((a, b) -> b.getEmails().size() - a.getEmails().size()).collect(Collectors.toList());
    }

    @Override
    public List<Product> sortBySizeAsc() {
        return this.productRepository.sortBySizeAsc();
    }

    @Override
    public List<Product> sortBySizeDesc() {
        return this.productRepository.sortBySizeDesc();
    }

        @Override
    public void addEmail(Long productId, String email) {
        Product product = getProductById(productId);

        product.addEmail(email);

        this.productRepository.save(product);
    }

    @Override
    public void removeEmail(Long productId, String email) {
        Product product = getProductById(productId);
        Set<String> emails = product.getEmails();

        boolean hasEmail = emails.contains(email);

        if (!hasEmail) {
            throw new EmailError(EMAIL_NOT_SUBSCRIBED);
        }
    }
//    @Override
//    public void updateName(Long productId, String name) {
//        Product product = getProductById(productId);
//        product.setName(name);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateDescription(Long productId, String description) {
//        Product product = getProductById(productId);
//        product.setDescription(description);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updatePrice(Long productId, Double price) {
//        Product product = getProductById(productId);
//        product.setPrice(price);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateWidth(Long productId, Integer width) {
//        Product product = getProductById(productId);
//        product.setWidth(width);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateHeight(Long productId, Integer height) {
//        Product product = getProductById(productId);
//        product.setHeight(height);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateWeight(Long productId, Integer weight) {
//        Product product = getProductById(productId);
//        product.setWeight(weight);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateAvailableQuantity(Long productId, Integer quantity) {
//        if(quantity < 0) {
//            quantity = 0;
//        }
//
//        Product product = getProductById(productId);
//        product.setQuantity(quantity);
//
//        this.productRepository.save(product);
//    }
//
//
//        emails.remove(email);
//        product.setEmails(emails);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void addImage(Long productId, String imgUrl) {
//        Product product = getProductById(productId);
//
//        product.addAdditionalImage(imgUrl);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateImgUrl(Long productId, String imgUrl) {
//        Product product = getProductById(productId);
//        product.setImageUrl(imgUrl);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateMaterial(Long productId, ProductMaterial material) {
//        Product product = getProductById(productId);
//        product.setMaterial(material);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public void updateCategory(Long productId, ProductCategory category) {
//        Product product = getProductById(productId);
//        product.setCategory(category);
//
//        this.productRepository.save(product);
//    }
//
//    @Override
//    public boolean removeImgUrl(Long productId, String imgUrl) {
//        Product product = getProductById(productId);
//
//        if(!product.getAdditionalImgUrls().contains(imgUrl)) {
//            return false;
//        }
//
//        product.getAdditionalImgUrls().remove(imgUrl);
//
//        this.productRepository.save(product);
//        return true;
//    }

    @Override
    public void updateProduct(Product product) {
        product.validate();

        this.productRepository.save(product);
    }
}
