package com.WoodStore.controllers;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AdminController {
    @GetMapping("/admin")
    public ResponseEntity<String> getAdminPage() {
        return ResponseEntity.ok("Welcome to the admin page!");
    }

    @GetMapping("/login")
    public ResponseEntity<String> getLoginPage(HttpServletResponse response) {
        return ResponseEntity.ok("Please login to access the admin page.");
    }
}
