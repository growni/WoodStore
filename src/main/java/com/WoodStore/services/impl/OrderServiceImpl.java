package com.WoodStore.services.impl;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Carrier;
import com.WoodStore.entities.Order;
import com.WoodStore.entities.Recipient;
import com.WoodStore.exceptions.BasketError;
import com.WoodStore.exceptions.OrderError;
import com.WoodStore.repositories.BasketRepository;
import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.WoodStore.messages.Errors.BASKET_NOT_FOUND;
import static com.WoodStore.messages.Errors.ORDER_NOT_FOUND_ERROR;
import static com.WoodStore.messages.email.Body.ORDER_CONFIRMATION_BODY;
import static com.WoodStore.messages.email.Subject.ORDER_CONFIRMATION_SUBJECT;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;
    private final BasketRepository basketRepository;
    private final EmailService emailService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, BasketRepository basketRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.basketRepository = basketRepository;
        this.emailService = emailService;
    }

    @Override
    public Order create() {
        return this.orderRepository.save(new Order());
    }

    @Override
    public Order getOrder(Long id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new OrderError(String.format(ORDER_NOT_FOUND_ERROR, id)));
    }

    @Override
    public void addRecipient(Long orderId, Recipient recipient) {
        Order order = getOrder(orderId);
        order.setRecipient(recipient);

        this.orderRepository.save(order);
    }

    @Override
    public void addCarrier(Long orderId, Carrier carrier) {
        Order order = getOrder(orderId);
        order.setCarrier(carrier);

        this.orderRepository.save(order);
    }

    @Override
    public void addBasket(Long orderId, Long basketId) {
        Order order = getOrder(orderId);
        Basket basket = this.basketRepository.findById(basketId).orElseThrow(() -> new BasketError(String.format(BASKET_NOT_FOUND, basketId)));

        order.setBasket(basket);

        this.orderRepository.save(order);
    }

    @Override
    public void place(Long orderId) {
        Order order = getOrder(orderId);

        order.validate();
        order.setStatus(OrderStatus.ORDERED);

        this.orderRepository.save(order);

        this.emailService.sendEmail(order.getRecipient().getEmail(), ORDER_CONFIRMATION_SUBJECT, String.format(ORDER_CONFIRMATION_BODY, order.getRecipient().getFirstName()));
    }
}
