package com.WoodStore;

import com.WoodStore.entities.Product;
import com.WoodStore.repositories.ProductRepository;
import com.WoodStore.services.ProductService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class Runner implements ApplicationRunner {
    private final ProductService productService;
    private final ProductRepository productRepository;

    public Runner(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        addEntities();
    }

    public void addEntities() {
        Random random = new Random();

        String[] names = {"Chair", "Table", "Lamp", "Sofa", "Bed", "Desk", "Shelf", "Cupboard", "Stool", "Bench"};
        String[] descriptions = {"Kitchen chair small", "Dining table large", "Desk lamp", "Living room sofa", "Queen size bed", "Office desk", "Wooden shelf", "Tall cupboard", "Bar stool", "Garden bench"};

        for (int i = 0; i < 20; i++) {
            StringBuilder sb = new StringBuilder();
            Product product = new Product();
            product.setName(names[random.nextInt(names.length)] + i);
            product.setDescription(descriptions[random.nextInt(descriptions.length)]);
            product.setPrice(50.0 + (200.0 - 50.0) * random.nextDouble());
            product.setWidth(50 + random.nextInt(451));
            product.setHeight(50 + random.nextInt(151));
            product.setWeight(5 + random.nextInt(46));
            product.setQuantity(1 + random.nextInt(100));

            int emailsCount = random.nextInt(names.length - 1);
            Set<String> productEmails = product.getEmails();

            for (int j = 0; j < emailsCount; j++) {
                String email = String.valueOf(sb.append(names[j]).append("@").append("gmail.com"));
                productEmails.add(email);
            }
            product.setEmails(productEmails);

            this.productService.addProduct(product);
        }


    }


}