package com.WoodStore;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.entities.Product;
import com.WoodStore.entities.Role;
import com.WoodStore.entities.UserEntity;
import com.WoodStore.repositories.RoleRepository;
import com.WoodStore.repositories.UserRepository;
import com.WoodStore.services.OrderService;
import com.WoodStore.services.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Runner implements ApplicationRunner {
    private final ProductService productService;

    private final OrderService orderService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Runner(ProductService productService, OrderService orderService, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.orderService = orderService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addEntities();

    }

    @Transactional
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
            product.setPrice(100 + (10000.0 - 100.0) * random.nextDouble());
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

            Set<String> additionalImages = new HashSet<>();
            this.productService.addProduct(product);

            int imageCount = 1 + random.nextInt(3);

            for (int j = 0; j < imageCount; j++) {
                String imageUrl = "http://example.com/images/additional" + random.nextInt(1000) + ".jpg";
                product.addAdditionalImage(imageUrl);
            }
            productService.updateProduct(product);

        }

        this.userRepository.save(new UserEntity("admin", passwordEncoder.encode("admin"), new HashSet<>(Collections.singleton(new Role("ADMIN")))));
        this.roleRepository.save(new Role("EMPLOYEE"));
        this.roleRepository.save(new Role("USER"));
//        createRoleIfNotFound("ADMIN");
//        createRoleIfNotFound("USER");
//
//        addDefaultAdminUser();
    }

    @Transactional
    public void addDefaultAdminUser() {

        String username = "admin";
        String password = passwordEncoder.encode("admin");
        Role userRole = this.roleRepository.findByName("USER").get();
        Role adminRole = this.roleRepository.findByName("ADMIN").get();

        UserEntity user = new UserEntity(username, password, new HashSet<>());
        user.getRoles().add(adminRole);
        user.getRoles().add(userRole);
        this.userRepository.save(user);
        this.roleRepository.save(adminRole);
        this.roleRepository.save(userRole);
    }



    @Transactional
    Role createRoleIfNotFound(String roleName) {
        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        return roleOptional.orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

}
