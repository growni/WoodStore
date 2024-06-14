package com.WoodStore.services.impl;

import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.*;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.WoodStore.constants.Constants.*;
import static com.WoodStore.messages.Errors.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        validateProduct(product);

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

        if(products.isEmpty()) {
            return null;
        }

        return products;
    }

    @Override
    public List<Product> sortByPriceDesc() {
        List<Product> products = this.productRepository.sortByPriceDesc();

        if(products.isEmpty()) {
            return null;
        }

        return products;
    }

    @Override
    public List<Product> sortByFavorites() {
        List<Product> products = this.productRepository.findAllSubscribedProducts();

        if(products.isEmpty()) {
            return null;
        }

        return products.stream().sorted((a, b) -> b.getEmails().size() - a.getEmails().size()).collect(Collectors.toList());
    }

    @Override
    public void updateName(Long productId, String name) {
        validateName(name);

        Product product = getProductById(productId);
        product.setName(name);

        this.productRepository.save(product);
    }

    @Override
    public void updateDescription(Long productId, String description) {
        validateDescription(description);

        Product product = getProductById(productId);
        product.setDescription(description);

        this.productRepository.save(product);
    }

    @Override
    public void updatePrice(Long productId, Double price) {
        validatePrice(price);

        Product product = getProductById(productId);
        product.setPrice(price);

        this.productRepository.save(product);
    }

    @Override
    public void updateWidth(Long productId, Integer width) {
        validateWidth(width);

        Product product = getProductById(productId);
        product.setWidth(width);

        this.productRepository.save(product);
    }

    @Override
    public void updateHeight(Long productId, Integer height) {
        validateHeight(height);

        Product product = getProductById(productId);
        product.setHeight(height);

        this.productRepository.save(product);
    }

    @Override
    public void updateWeight(Long productId, Integer weight) {
        validateWeight(weight);

        Product product = getProductById(productId);
        product.setWeight(weight);

        this.productRepository.save(product);
    }

    @Override
    public void updateAvailableQuantity(Long productId, Integer quantity) {
        validateQuantity(quantity);

        Product product = getProductById(productId);
        product.setQuantity(quantity);

        this.productRepository.save(product);
    }

    @Override
    public void addEmail(Long productId, String email) {
        validateEmail(email);

        Product product = getProductById(productId);
        product.getEmails().add(email);

        this.productRepository.save(product);
    }

    @Override
    public void removeEmail(Long productId, String email) {
        validateEmail(email);

        Product product = getProductById(productId);
        Set<String> emails = product.getEmails();

        boolean hasEmail = emails.contains(email);

        if(!hasEmail) {
            throw new EmailError(EMAIL_NOT_SUBSCRIBED);
        }

        emails.remove(email);
        product.setEmails(emails);

        this.productRepository.save(product);
    }

    private void validateEmail(String email) {
        String emailRegexPattern = "^(.+)@(\\S+)$";
        boolean isValidEmail = Pattern.compile(emailRegexPattern).matcher(email).matches();

        if(!isValidEmail) {
            throw new EmailError(INVALID_EMAIL_ERROR);
        }

    }

    private void validateName(String name) {
        if(name == null || name.trim().length() == PRODUCT_NAME_MIN_LENGTH || name.trim().length() > PRODUCT_NAME_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_NAME_LENGTH_ERROR);
        }

        List<Product> products = findProductsByName(name);

        if(products.size() > 0) {
            throw new ProductPropertyError(PRODUCT_NAME_ALREADY_EXISTS);
        }
    }

    private void validateDescription(String description) {
        if(description.trim().length() > PRODUCT_DESCRIPTION_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_DESCRIPTION_LENGTH_ERROR);
        }
    }

    private void validatePrice(Double price) {
        if(price == null || price.isNaN() || price < PRODUCT_PRICE_MIN || price > PRODUCT_PRICE_MAX) {
            throw new ProductPropertyError(PRODUCT_PRICE_ERROR);
        }
    }

    private void validateWidth(Integer width) {
        if(width < PRODUCT_WIDTH_MIN || width > PRODUCT_WIDTH_MAX) {
            throw new ProductPropertyError(PRODUCT_WIDTH_ERROR);
        }
    }

    private void validateHeight(Integer height) {
        if(height < PRODUCT_HEIGHT_MIN || height > PRODUCT_HEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_HEIGHT_ERROR);
        }
    }

    private void validateWeight(Integer weight) {
        if(weight < PRODUCT_WEIGHT_MIN || weight > PRODUCT_WEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_WEIGHT_ERROR);
        }
    }

    private void validateQuantity(Integer quantity) {
        if(quantity == null || quantity < PRODUCT_QUANTITY_MIN || quantity > PRODUCT_QUANTITY_MAX) {
            throw new ProductPropertyError(PRODUCT_QUANTITY_ERROR);
        }
    }

    private void validateProduct(Product product) {
        validateName(product.getName());
        validateDescription(product.getDescription());
        validatePrice(product.getPrice());
        validateWidth(product.getWidth());
        validateHeight(product.getHeight());
        validateWeight(product.getWeight());
        validateQuantity(product.getQuantity());
    }
}
