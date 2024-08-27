package com.WoodStore.services.impl;

import com.WoodStore.entities.Basket;
import com.WoodStore.entities.BasketItem;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.dtos.BasketDto;
import com.WoodStore.entities.dtos.BasketItemDto;
import com.WoodStore.exceptions.BasketError;
import com.WoodStore.exceptions.ProductNotFound;
import com.WoodStore.exceptions.ProductPropertyError;
import com.WoodStore.repositories.BasketItemRepository;
import com.WoodStore.repositories.BasketRepository;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.BasketService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.WoodStore.messages.errors.OrderErrors.BASKET_NOT_FOUND;
import static com.WoodStore.messages.errors.ProductErrors.*;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketItemRepository basketItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BasketServiceImpl(BasketRepository basketRepository, BasketItemRepository basketItemRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.basketItemRepository = basketItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Basket createBasket(BasketDto basketDto) {
        Basket basket = new Basket();
        for (BasketItemDto basketItemDto : basketDto.getItems()) {
            Product product = productRepository.findById(basketItemDto.getProductId()).orElseThrow(() ->
                    new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, basketItemDto.getProductId())));
            basket.addItem(product, basketItemDto.getQuantity());
        }
        return this.basketRepository.save(basket);
    }

    @Override
    public Basket getBasket(Long basketId) {
        return this.basketRepository.findById(basketId).orElseThrow(() -> new BasketError(String.format(BASKET_NOT_FOUND, basketId)));
    }

    @Override
    public void addProduct(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));

        basket.addItem(product, 1);

        BasketItem item = basket.getItem(product);

        this.basketItemRepository.save(item);
        this.basketRepository.save(basket);
    }

    @Override
    public void increaseQuantity(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));
        BasketItem item = basket.getItem(product);

        if(product.getQuantity() < item.getQuantity()) {
            throw new ProductPropertyError(PRODUCT_OUT_OF_STOCK_ERROR);
        }

        item.setQuantity(item.getQuantity() + 1);

        this.basketItemRepository.save(item);
        this.basketRepository.save(basket);
    }

    @Override
    public void decreaseQuantity(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));

        BasketItem item = basket.getItem(product);
        item.setQuantity(item.getQuantity() - 1);

        if(item.getQuantity() == 0) {
            basket.removeItem(product);
        }

        this.basketItemRepository.save(item);
        this.basketRepository.save(basket);
    }

    @Override
    public void removeProduct(Long basketId, Long productId) {
        Basket basket = getBasket(basketId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ProductNotFound(String.format(PRODUCT_NOT_FOUND_ERROR, productId)));

        basket.removeItem(product);

        this.basketRepository.save(basket);
    }

    @Override
    public void clear(Long basketId) {
        Basket basket = getBasket(basketId);
        basket.clear();

        this.basketRepository.save(basket);
    }
}
