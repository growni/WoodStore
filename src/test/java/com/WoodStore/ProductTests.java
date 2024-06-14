package com.WoodStore;

import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.ProductNotFound;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class ProductTests {

        @Mock
        private ProductRepository productRepository;

        @InjectMocks
        private ProductServiceImpl productService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testAddProduct() {
            Product product = new Product();
            product.setId(1L);
            product.setName("Test Product Name");
            product.setDescription("Test Product Description");
            product.setPrice(300.0);
            product.setWidth(120);
            product.setHeight(70);
            product.setWeight(7);
            product.setQuantity(5);

            when(productRepository.save(any(Product.class))).thenReturn(product);

            Product savedProduct = productService.addProduct(product);

            assertNotNull(savedProduct);
            assertEquals("Test Product Name", savedProduct.getName());
            assertEquals("Test Product Description", savedProduct.getDescription());
            assertEquals(300.0, savedProduct.getPrice());
            assertEquals(120, savedProduct.getWidth());
            assertEquals(70, savedProduct.getHeight());
            assertEquals(7, savedProduct.getWeight());
            assertEquals(5, savedProduct.getQuantity());
            verify(productRepository, times(1)).save(product);
        }

        @Test
        public void testGetAllProducts() {
            Product product1 = new Product();
            product1.setId(1L);
            product1.setName("Product 1");

            Product product2 = new Product();
            product2.setId(2L);
            product2.setName("Product 2");

            List<Product> products = Arrays.asList(product1, product2);

            when(productRepository.findAll()).thenReturn(products);

            List<Product> result = productService.getAllProducts();

            assertEquals(2, result.size());
            verify(productRepository, times(1)).findAll();
        }

        @Test
        public void testGetProductById() {
            Product product = new Product();
            product.setId(1L);
            product.setName("Test Product");

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));

            Product foundProduct = productService.getProductById(1L);

            assertNotNull(foundProduct);
            assertEquals("Test Product", foundProduct.getName());
            verify(productRepository, times(1)).findById(1L);
        }

        @Test
        public void testGetProductByIdNotFound() {
            when(productRepository.findById(1L)).thenReturn(Optional.empty());

            Exception exception = assertThrows(ProductNotFound.class, () -> {
                productService.getProductById(1L);
            });

            String expectedMessage = String.format("Product with id %d not found", 1L);
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
            verify(productRepository, times(1)).findById(1L);
        }

        @Test
        public void testDeleteProductById() {
            productService.deleteProductById(1L);
            verify(productRepository, times(1)).deleteById(1L);
        }

        @Test
        public void testFindProductsByName() {
            Product product = new Product();
            product.setId(1L);
            product.setName("Test Product");

            List<Product> products = Arrays.asList(product);

            when(productRepository.findProductsByName("Test Product")).thenReturn(products);

            List<Product> result = productService.findProductsByName("Test Product");

            assertEquals(1, result.size());
            assertEquals("Test Product", result.get(0).getName());
            verify(productRepository, times(1)).findProductsByName("Test Product");
        }

        @Test
        public void testUpdateAvailableQuantity() {
            Long productId = 1L;
            int newQuantity = 10;
            Product product = new Product();
            product.setId(productId);
            product.setQuantity(5); // Initial quantity

            when(productRepository.findById(productId)).thenReturn(Optional.of(product));
            when(productRepository.save(product)).thenReturn(product);

            productService.updateAvailableQuantity(productId, newQuantity);

            assertEquals(newQuantity, product.getQuantity());
            verify(productRepository, times(1)).findById(productId);
            verify(productRepository, times(1)).save(product);
        }

        @Test
        public void testUpdateAvailableQuantityProductNotFound() {
            Long productId = 1L;
            int newQuantity = 10;

            when(productRepository.findById(productId)).thenReturn(Optional.empty());

            assertThrows(ProductNotFound.class, () -> {
                productService.updateAvailableQuantity(productId, newQuantity);
            });

            verify(productRepository, times(1)).findById(productId);
            verify(productRepository, never()).save(any(Product.class));
        }

}
