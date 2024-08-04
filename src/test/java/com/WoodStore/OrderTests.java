package com.WoodStore;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.*;
import com.WoodStore.exceptions.EmailError;
import com.WoodStore.exceptions.OrderError;
import com.WoodStore.exceptions.ProductNotFound;
import com.WoodStore.exceptions.ProductPropertyError;
import com.WoodStore.repositories.BasketRepository;
import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.impl.EmailService;
import com.WoodStore.services.impl.OrderServiceImpl;
import com.WoodStore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.validation.Validator;
import java.util.*;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class OrderTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order createdOrder = orderService.create();

        assertNotNull(createdOrder);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testGetOrder() {
        Order order = new Order();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order fetchedOrder = orderService.getOrder(1L);

        assertNotNull(fetchedOrder);
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testAddRecipient() {
        Order order = new Order();
        Recipient recipient = new Recipient("Georgi", "Aleksandrov", "gosho22@gmail.com", "123456789");
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.addRecipient(1L, recipient);

        assertEquals(recipient, order.getRecipient());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testAddCarrier() {
        Order order = new Order();
        Carrier carrier = new Carrier("North", "City", "123 Street");
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.addCarrier(1L, carrier);

        assertEquals(carrier, order.getCarrier());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testAddBasket() {
        Order order = new Order();
        Basket basket = new Basket();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(basketRepository.findById(anyLong())).thenReturn(Optional.of(basket));

        orderService.addBasket(1L, 1L);

        assertEquals(basket, order.getBasket());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testPlaceOrder() {
        Order order = new Order();
        Recipient recipient = new Recipient("Georgi", "Aleksandrov", "gosho22@gmaill.com", "123456789");
        Carrier carrier = new Carrier("Sofia", "Sofia", "zh.k. krasna polyana 2, 221");
        Basket basket = new Basket();
        Product product = new Product(1L, "everyday chair", "dining chair", 222.99, 20, 20, 20, 10, "image url", ProductMaterial.BEECH, ProductCategory.INTERIOR, new HashSet<>(), new HashSet<>());

        basket.addItem(product, 2);
        order.setRecipient(recipient);
        order.setCarrier(carrier);
        order.setBasket(basket);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        orderService.place(1L);

        assertEquals(OrderStatus.ORDERED, order.getStatus());
        verify(orderRepository, times(1)).save(order);
        verify(emailService, times(1)).sendEmail(eq("gosho22@gmaill.com"), eq("Order Confirmation"), anyString());
    }

    @Test
    public void testPlaceOrderWithoutRecipient() {
        Order order = new Order();
        Carrier carrier = new Carrier("North", "City", "123 Street");
        Basket basket = new Basket();
        order.setCarrier(carrier);
        order.setBasket(basket);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(OrderError.class, () -> {
            orderService.place(1L);
        });

        String expectedMessage = "Recipient information is required to place the order.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPlaceOrderWithoutCarrier() {
        Order order = new Order();
        Recipient recipient = new Recipient("John", "Doe", "john.doe@example.com", "123456789");
        Basket basket = new Basket();
        order.setRecipient(recipient);
        order.setBasket(basket);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(OrderError.class, () -> {
            orderService.place(1L);
        });

        String expectedMessage = "Carrier information is required to place the order.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPlaceOrderWithoutBasket() {
        Order order = new Order();
        Recipient recipient = new Recipient("John", "Doe", "john.doe@example.com", "123456789");
        Carrier carrier = new Carrier("North", "City", "123 Street");
        order.setRecipient(recipient);
        order.setCarrier(carrier);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(OrderError.class, () -> {
            orderService.place(1L);
        });

        String expectedMessage = "Basket is required to place the order.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPlaceOrderWithInvalidRecipient() {
        Order order = new Order();
        Recipient recipient = new Recipient("", "Doe", "john.doe@example.com", "123456789");
        Carrier carrier = new Carrier("North", "City", "123 Street");
        Basket basket = new Basket();
        order.setRecipient(recipient);
        order.setCarrier(carrier);
        order.setBasket(basket);
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(OrderError.class, () -> {
            orderService.place(1L);
        });

        String expectedMessage = "Invalid order recipient first name.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testPlaceOrderWithInvalidCarrier() {
        Order order = new Order();
        Recipient recipient = new Recipient("John", "Doe", "john.doe@example.com", "123456789");
        Carrier carrier = new Carrier("", "City", "123 Street");
        Basket basket = new Basket();
        order.setRecipient(recipient);
        order.setCarrier(carrier);
        order.setBasket(basket);

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(OrderError.class, () -> {
            orderService.place(1L);
        });

        String expectedMessage = "Invalid order carrier region.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
