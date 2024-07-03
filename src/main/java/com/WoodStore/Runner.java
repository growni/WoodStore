package com.WoodStore;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Basket;
import com.WoodStore.entities.Product;
import com.WoodStore.services.BasketService;
import com.WoodStore.services.OrderService;
import com.WoodStore.services.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class Runner implements ApplicationRunner {
    private final ProductService productService;

    private final OrderService orderService;
    private final BasketService basketService;

    public Runner(ProductService productService, OrderService orderService, BasketService basketService) {
        this.productService = productService;
        this.orderService = orderService;
        this.basketService = basketService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addEntities();

    }

    public void addEntities() {
        Random random = new Random();

        String[] names = {"Chair", "Table", "Lamp", "Sofa", "Bed", "Desk", "Shelf", "Cupboard", "Stool", "Bench"};
        String[] descriptions = {"Kitchen chair small", "Dining table large", "Desk lamp", "Living room sofa", "Queen size bed", "Office desk", "Wooden shelf", "Tall cupboard", "Bar stool", "Garden bench"};
        String[] imageUrls = {
                "http://example.com/images/chair.jpg",
                "http://example.com/images/table.jpg",
                "http://example.com/images/lamp.jpg",
                "http://example.com/images/sofa.jpg",
                "http://example.com/images/bed.jpg",
                "http://example.com/images/desk.jpg",
                "http://example.com/images/shelf.jpg",
                "http://example.com/images/cupboard.jpg",
                "http://example.com/images/stool.jpg",
                "http://example.com/images/bench.jpg"
        };

        ProductMaterial[] materials = ProductMaterial.values();
        ProductCategory[] categories = ProductCategory.values();

        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setName(names[random.nextInt(names.length)] + i);
            product.setDescription(descriptions[random.nextInt(descriptions.length)]);
            product.setPrice(50.0 + (10000.0 - 50.0) * random.nextDouble());
            product.setWidth(50 + random.nextInt(451));
            product.setHeight(50 + random.nextInt(151));
            product.setWeight(5 + random.nextInt(46));
            product.setQuantity(1 + random.nextInt(100));

            product.setImageUrl(imageUrls[random.nextInt(imageUrls.length)]);
            product.setMaterial(materials[random.nextInt(materials.length)]);
            product.setCategory(categories[random.nextInt(categories.length)]);

            // Add random emails
            Set<String> emails = new HashSet<>();
            int emailCount = 1 + random.nextInt(3); // Adding 1 to 3 emails
            for (int j = 0; j < emailCount; j++) {
                emails.add("user" + random.nextInt(1000) + "@example.com");
            }
            product.setEmails(emails);

            // Add random additional images
            Set<String> additionalImages = new HashSet<>();
            this.productService.addProduct(product);

            int imageCount = 1 + random.nextInt(3); // Adding 1 to 3 additional images

            for (int j = 0; j < imageCount; j++) {
                this.productService.addImage(product.getId(), "http://example.com/images/additional" + random.nextInt(1000) + ".jpg");
            }


        }
    }


}
