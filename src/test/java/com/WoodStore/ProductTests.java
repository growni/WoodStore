package com.WoodStore;

import com.WoodStore.entities.Product;
import com.WoodStore.exceptions.EmailError;
import com.WoodStore.exceptions.ProductNotFound;
import com.WoodStore.exceptions.ProductPropertyError;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.validation.Validator;
import java.util.*;

import static com.WoodStore.constants.Constants.PRODUCT_DESCRIPTION_MAX_LENGTH;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    public void testFindAllByPriceCheaperThan() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Cheap Product 1");
        product1.setPrice(120.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Cheap Product 2");
        product2.setPrice(130.0);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Expensive Product");
        product3.setPrice(1000.0);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAllByPriceLessThan(150.0)).thenReturn(products);

        List<Product> result = productService.findProductsCheaperThan(150.0);

        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        verify(productRepository, times(1)).findAllByPriceLessThan(150.0);
    }

    @Test
    public void testFindAllByPriceCheaperThanNoResults() {
        Double price = 20.0;
        when(productRepository.findAllByPriceLessThan(price)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ProductNotFound.class, () -> {
            productService.findProductsCheaperThan(price);
        });

        String expectedMessage = String.format("There are no products with price less than %.2f.", price);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(1)).findAllByPriceLessThan(price);
    }

    @Test
    public void testFindAllByPriceMoreExpensiveThan() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Expensive Product 1");
        product1.setPrice(1500.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Expensive Product 2");
        product2.setPrice(2000.0);

        Product product3 = new Product();
        product3.setId(3L);
        product3.setName("Cheap Product");
        product3.setPrice(100.0);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAllByPriceGreaterThan(100.0)).thenReturn(products);

        List<Product> result = productService.findProductsMoreExpensiveThan(100.0);

        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        assertFalse(result.contains(product3));
        verify(productRepository, times(1)).findAllByPriceGreaterThan(100.0);
    }

    @Test
    public void testFindAllByPriceMoreExpensiveThanNoResults() {
        Double price = 300.0;
        when(productRepository.findAllByPriceGreaterThan(price)).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ProductNotFound.class, () -> {
            productService.findProductsMoreExpensiveThan(price);
        });

        String expectedMessage = String.format("There are no products with price greater than %.2f.", price);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(1)).findAllByPriceGreaterThan(price);
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
    public void testFindAvailableProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setQuantity(5);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setQuantity(10);

        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAvailableProducts()).thenReturn(products);

        List<Product> result = productService.findAvailableProducts();

        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));
        verify(productRepository, times(1)).findAvailableProducts();
    }

    @Test
    public void testFindAvailableProductsNoResults() {
        when(productRepository.findAvailableProducts()).thenReturn(Collections.emptyList());

        Exception exception = assertThrows(ProductNotFound.class, () -> {
            productService.findAvailableProducts();
        });

        String expectedMessage = "There are no available products at this time.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(1)).findAvailableProducts();
    }



    @Test
    public void testSortByPriceAsc() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(150.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(130.0);

        List<Product> products = Arrays.asList(product2, product1);  // List in ascending order

        when(productRepository.sortByPriceAsc()).thenReturn(products);

        List<Product> result = productService.sortByPriceAsc();

        assertEquals(2, result.size());
        assertEquals(product2, result.get(0));
        assertEquals(product1, result.get(1));
        verify(productRepository, times(1)).sortByPriceAsc();
    }

    @Test
    public void testSortByPriceAscNoResults() {
        when(productRepository.sortByPriceAsc()).thenReturn(Collections.emptyList());

        List<Product> result = productService.sortByPriceAsc();

        assertNull(result);
        verify(productRepository, times(1)).sortByPriceAsc();
    }

    @Test
    public void testSortByPriceDesc() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(9000.0);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(3000.0);

        List<Product> products = Arrays.asList(product1, product2);  // List in descending order

        when(productRepository.sortByPriceDesc()).thenReturn(products);

        List<Product> result = productService.sortByPriceDesc();

        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));
        verify(productRepository, times(1)).sortByPriceDesc();
    }

    @Test
    public void testSortByPriceDescNoResults() {
        when(productRepository.sortByPriceDesc()).thenReturn(Collections.emptyList());

        List<Product> result = productService.sortByPriceDesc();

        assertNull(result);
        verify(productRepository, times(1)).sortByPriceDesc();
    }

    @Test
    public void testSortByFavorites() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setEmails(new HashSet<>());

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setEmails(Set.of("email1@example.com", "email2@example.com"));

        List<Product> mockProducts = List.of(product1, product2);

        when(productRepository.findAllSubscribedProducts()).thenReturn(mockProducts);
        List<Product> sortedProducts = productService.sortByFavorites();

        assertNotNull(sortedProducts);
        assertEquals(2, sortedProducts.size());

        assertEquals("Product 2", sortedProducts.get(0).getName());
        assertEquals("Product 1", sortedProducts.get(1).getName());

        verify(productRepository, times(1)).findAllSubscribedProducts();
    }

    @Test
    public void testSortByFavoritesNoProducts() {
        when(productRepository.findAllSubscribedProducts()).thenReturn(new ArrayList<>());

        List<Product> sortedProducts = productService.sortByFavorites();
        assertNull(sortedProducts);

        verify(productRepository, times(1)).findAllSubscribedProducts();
    }

    @Test
    public void testSortByFavoritesNullProducts() {
        when(productRepository.findAllSubscribedProducts()).thenReturn(null);

        List<Product> sortedProducts = productService.sortByFavorites();
        assertNull(sortedProducts);

        verify(productRepository, times(1)).findAllSubscribedProducts();
    }

    @Test
    void testValidateName_ValidName_ShouldNotThrowException() {
        Product product = new Product();
        product.setName("Valid Product Name");
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateName_InvalidName_ShouldThrowException() {
        Product product = new Product();
        product.setName("A");
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateDescription_ValidDescription_ShouldNotThrowException() {
        Product product = new Product();
        product.setDescription("This is a valid description.");
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateDescription_TooLongDescription_ShouldThrowException() {
        Product product = new Product();
        String longDescription = "A".repeat(PRODUCT_DESCRIPTION_MAX_LENGTH + 1);
        product.setDescription(longDescription);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidatePrice_ValidPrice_ShouldNotThrowException() {
        Product product = new Product();
        product.setPrice(500.0);
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidatePrice_InvalidPrice_ShouldThrowException() {
        Product product = new Product();
        product.setPrice(-100.0);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateWidth_ValidWidth_ShouldNotThrowException() {
        Product product = new Product();
        product.setWidth(100);
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateWidth_InvalidWidth_ShouldThrowException() {
        Product product = new Product();
        product.setWidth(-10);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateHeight_ValidHeight_ShouldNotThrowException() {
        Product product = new Product();
        product.setHeight(100);
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateHeight_InvalidHeight_ShouldThrowException() {
        Product product = new Product();
        product.setHeight(-10);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateWeight_ValidWeight_ShouldNotThrowException() {
        Product product = new Product();
        product.setWeight(50);
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateWeight_InvalidWeight_ShouldThrowException() {
        Product product = new Product();
        product.setWeight(-1);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateQuantity_ValidQuantity_ShouldNotThrowException() {
        Product product = new Product();
        product.setQuantity(10);
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateQuantity_InvalidQuantity_ShouldThrowException() {
        Product product = new Product();
        product.setQuantity(-1);
        assertThrows(ProductPropertyError.class, product::validate);
    }

    @Test
    void testValidateImageUrl_ValidUrl_ShouldNotThrowException() {
        Product product = new Product();
        product.setImageUrl("http://example.com/image.jpg");
        assertDoesNotThrow(product::validate);
    }

    @Test
    void testValidateImageUrl_InvalidUrl_ShouldThrowException() {
        Product product = new Product();
        product.setImageUrl("invalid-url");
        assertThrows(ProductPropertyError.class, product::validate);
    }

//    @Test
//    public void testAddEmail() {
//        Long productId = 1L;
//        String email = "test@example.com";
//        Product product = new Product();
//        product.setId(productId);
//        product.setEmails(new HashSet<>());
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.addEmail(productId, email);
//
//        assertTrue(product.getEmails().contains(email));
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testAddEmailInvalidEmail() {
//        Long productId = 1L;
//        String invalidEmail = "invalid-email";
//
//        Product product = new Product();
//        product.setId(productId);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        assertThrows(EmailError.class, () -> {
//            productService.addEmail(productId, invalidEmail);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testAddEmailProductNotFound() {
//        Long productId = 1L;
//        String email = "test@example.com";
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.addEmail(productId, email);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testRemoveEmail() {
//        Long productId = 1L;
//        String email = "test@example.com";
//        Set<String> emails = new HashSet<>(Arrays.asList(email));
//        Product product = new Product();
//        product.setId(productId);
//        product.setEmails(emails);
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.removeEmail(productId, email);
//
//        assertFalse(product.getEmails().contains(email));
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testRemoveEmailNotSubscribed() {
//        Long productId = 1L;
//        String email = "test@example.com";
//        Set<String> emails = new HashSet<>();
//        Product product = new Product();
//        product.setId(productId);
//        product.setEmails(emails);
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        Exception exception = assertThrows(EmailError.class, () -> {
//            productService.removeEmail(productId, email);
//        });
//
//        String expectedMessage = "The email is not subscribed for this product.";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(product);
//    }
//
//    @Test
//    public void testRemoveEmailInvalidEmail() {
//        Long productId = 1L;
//        String invalidEmail = "invalid-email";
//
//        Product product = new Product();
//        product.setId(productId);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        assertThrows(EmailError.class, () -> {
//            productService.removeEmail(productId, invalidEmail);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testRemoveEmailProductNotFound() {
//        Long productId = 1L;
//        String email = "test@example.com";
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.removeEmail(productId, email);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateWidth() {
//        Long productId = 1L;
//        Integer newWidth = 150;
//        Product product = new Product();
//        product.setId(productId);
//        product.setWidth(100); // Initial width
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.updateWidth(productId, newWidth);
//
//        assertEquals(newWidth, product.getWidth());
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testUpdateWidthInvalidWidth() {
//        Long productId = 1L;
//        Integer invalidWidth = -5;
//
//        Product product = new Product();
//        product.setId(productId);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        assertThrows(ProductPropertyError.class, () -> {
//            productService.updateWeight(productId, invalidWidth);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateWidthProductNotFound() {
//        Long productId = 1L;
//        Integer newWidth = 150;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.updateWidth(productId, newWidth);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateHeight() {
//        Long productId = 1L;
//        Integer newHeight = 200;
//        Product product = new Product();
//        product.setId(productId);
//        product.setHeight(100); // Initial height
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.updateHeight(productId, newHeight);
//
//        assertEquals(newHeight, product.getHeight());
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testUpdateHeightInvalidHeight() {
//        Long productId = 1L;
//        Integer invalidHeight= -5;
//
//        Product product = new Product();
//        product.setId(productId);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        assertThrows(ProductPropertyError.class, () -> {
//            productService.updateWeight(productId, invalidHeight);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateHeightProductNotFound() {
//        Long productId = 1L;
//        Integer newHeight = 200;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.updateHeight(productId, newHeight);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateWeight() {
//        Long productId = 1L;
//        Integer newWeight = 50;
//        Product product = new Product();
//        product.setId(productId);
//        product.setWeight(20); // Initial weight
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.updateWeight(productId, newWeight);
//
//        assertEquals(newWeight, product.getWeight());
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testUpdateWeightInvalidWeight() {
//        Long productId = 1L;
//        Integer invalidWeight = -5;
//
//        Product product = new Product();
//        product.setId(productId);
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        assertThrows(ProductPropertyError.class, () -> {
//            productService.updateWeight(productId, invalidWeight);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testUpdateWeightProductNotFound() {
//        Long productId = 1L;
//        Integer newWeight = 50;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.updateWeight(productId, newWeight);
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
//
//    @Test
//    public void testAddImage() {
//        Long productId = 1L;
//        String imgUrl = "http://example.com/image.jpg";
//        Product product = new Product();
//        product.setId(productId);
//        product.setAdditionalImgUrls(new HashSet<>());
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        productService.addImage(productId, imgUrl);
//
//        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
//        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
//        Product savedProduct = productArgumentCaptor.getValue();
//
//        assertTrue(savedProduct.getAdditionalImgUrls().contains(imgUrl));
//    }
//
//    @Test
//    public void testUpdateImgUrl() {
//        Long productId = 1L;
//        String imgUrl = "http://example.com/newimage.jpg";
//        Product product = new Product();
//        product.setId(productId);
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//
//        productService.updateImgUrl(productId, imgUrl);
//
//        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
//        verify(productRepository, times(1)).save(productArgumentCaptor.capture());
//        Product savedProduct = productArgumentCaptor.getValue();
//
//        assertEquals(imgUrl, savedProduct.getImageUrl());
//    }
//
//    @Test
//    public void testAddImage_ProductNotFound() {
//        Long productId = 1L;
//        String imgUrl = "http://example.com/image.jpg";
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> productService.addImage(productId, imgUrl));
//    }
//
//    @Test
//    public void testUpdateImgUrl_ProductNotFound() {
//        Long productId = 1L;
//        String imgUrl = "http://example.com/newimage.jpg";
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> productService.updateImgUrl(productId, imgUrl));
//    }

//    @Test
//    public void testUpdateAvailableQuantity() {
//        Long productId = 1L;
//        int newQuantity = 10;
//        Product product = new Product();
//        product.setId(productId);
//        product.setQuantity(5); // Initial quantity
//
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(productRepository.save(product)).thenReturn(product);
//
//        productService.updateProduct(product);
//
//        assertEquals(newQuantity, product.getQuantity());
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, times(1)).save(product);
//    }
//
//    @Test
//    public void testUpdateAvailableQuantityProductNotFound() {
//        Long productId = 1L;
//        int newQuantity = 10;
//
//        when(productRepository.findById(productId)).thenReturn(Optional.empty());
//
//        assertThrows(ProductNotFound.class, () -> {
//            productService.updateProduct();
//        });
//
//        verify(productRepository, times(1)).findById(productId);
//        verify(productRepository, never()).save(any(Product.class));
//    }
}
