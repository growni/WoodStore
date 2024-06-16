package com.WoodStore.services.impl;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.OrderProductKey;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.dtos.OrderProduct;
import com.WoodStore.exceptions.OrderNotFound;
import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.services.OrderService;
import com.WoodStore.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.WoodStore.messages.Errors.ORDER_NOT_FOUND_ERROR;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    @Transactional
    public Order createOrder(Set<Product> products) { //TODO: Should be able to execute with a single save to the DB
        Order order = new Order();
        this.orderRepository.save(order);

        Set<OrderProduct> orderProducts = products.stream().map(product ->
                new OrderProduct(new OrderProductKey(order.getId(), product.getId()), order, product)
        ).collect(Collectors.toSet());

        order.setOrderProducts(orderProducts);

        return this.orderRepository.save(order);
    }

    @Override
    public void placeOrder(Long orderId) {
        Order order = getOrderById(orderId);
        Set<OrderProduct> orderProducts = order.getOrderProducts();

        for (OrderProduct op : orderProducts) {
            Product productDB = this.productService.getProductById(op.getProduct().getId());

            Integer availableQuantity = productDB.getQuantity();
            int updatedQuantity = availableQuantity - op.getQuantity();

            this.productService.updateAvailableQuantity(productDB.getId(), updatedQuantity);
        }

        updateStatus(orderId, OrderStatus.ORDERED);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound(String.format(ORDER_NOT_FOUND_ERROR, orderId)));
    }

    @Override
    public Set<Order> getAllOrders() {
        return new HashSet<>(this.orderRepository.findAll());
    }

    @Override
    public void updateStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);

        this.orderRepository.save(order);
    }
}
