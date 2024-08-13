package com.WoodStore.controllers;


import com.WoodStore.entities.Product;
import com.WoodStore.entities.Role;
import com.WoodStore.entities.UserEntity;
import com.WoodStore.entities.dtos.RegisterDto;
import com.WoodStore.repositories.RoleRepository;
import com.WoodStore.repositories.UserRepository;
import com.WoodStore.services.ProductService;
import com.WoodStore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ProductService productService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();

        registerDto.validatePassword(registerDto.getPassword());
        registerDto.validateUsername(registerDto.getUsername());

        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PatchMapping("/user/update/username")
    public void changeUsername(@RequestBody RegisterDto registerDto) {
        this.userService.changeUsername(registerDto);
    }

    @PatchMapping("/user/update/password")
    public void changePassword(@RequestBody RegisterDto registerDto) {
        this.userService.changePassword(registerDto);
    }

    @DeleteMapping("/user/delete")
    public void deleteUser(@RequestParam Long userId) {
        this.userService.deleteUser(userId);
    }

    @PatchMapping("/product/update/name")
    public void updateProductName(@RequestBody Product product) {
        this.productService.updateName(product.getId(), product.getName());
    }

    @PatchMapping("/product/update/description")
    public void updateProductDescription(@RequestBody Product product) {
        this.productService.updateDescription(product.getId(), product.getDescription());
    }

    @PatchMapping("/product/update/price")
    public void updateProductPrice(@RequestBody Product product) {
        this.productService.updatePrice(product.getId(), product.getPrice());
    }

    @PatchMapping("/product/update/quantity")
    public void updateAvailableQuantity(@RequestBody Product product) {
        this.productService.updateAvailableQuantity(product.getId(), product.getQuantity());
    }

    @PatchMapping("/product/update/width")
    public void updateProductWidth(@RequestBody Product product) {
        this.productService.updateWidth(product.getId(), product.getWidth());
    }

    @PatchMapping("/product/update/height")
    public void updateProductHeight(@RequestBody Product product) {
        this.productService.updateHeight(product.getId(), product.getHeight());
    }

    @PatchMapping("/product/update/weight")
    public void updateProductWeight(@RequestBody Product product) {
        this.productService.updateWeight(product.getId(), product.getWeight());
    }

    @PatchMapping("/product/update/image")
    public void updateImgUrl(@RequestBody Product product) {
        this.productService.updateImgUrl(product.getId(), product.getImageUrl());
    }

    @PatchMapping("/product/update/image/add")
    public void addImage(@RequestBody Product product) {
        this.productService.addImage(product.getId(), product.getImageUrl());
    }

    @DeleteMapping("/product/update/image/remove")
    public boolean removeImage(@RequestBody Product product) {
        return this.productService.removeImgUrl(product.getId(), product.getImageUrl());
    }
}
