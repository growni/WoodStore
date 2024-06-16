package com.WoodStore;

import com.WoodStore.entities.Order;
import com.WoodStore.entities.dtos.OrderProduct;
import com.WoodStore.entities.OrderProductKey;
import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.ProductNotFound;
import com.WoodStore.repositories.OrderRepository;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.impl.OrderServiceImpl;
import com.WoodStore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



public class OrderTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    @InjectMocks
    private ProductServiceImpl productService;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrder() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");

        Set<Product> products = new HashSet<>();
        products.add(product1);
        products.add(product2);

        Order order = new Order();
        order.setId(1L);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            if (savedOrder.getId() == null) {
                savedOrder.setId(1L); // Simulate ID generation
            }
            return savedOrder;
        });

        Order savedOrder = orderService.createOrder(products);

        assertEquals(1L, savedOrder.getId());
        assertEquals(2, savedOrder.getOrderProducts().size());

        verify(orderRepository, times(2)).save(any(Order.class));
    }

}
