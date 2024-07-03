package com.WoodStore.entities;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.utils.SetToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    private Double price;
    private Integer width;
    private Integer height;
    private Integer weight;
    private Integer quantity;
    @Column(name = "image_url")
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private ProductMaterial material;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;


    @Convert(converter = SetToStringConverter.class)
    @Column(name = "additional_images")
    private Set<String> additionalImgUrls;

    @Convert(converter = SetToStringConverter.class)
    @Column(name = "emails", length = 2048)
    private Set<String> emails;

    public Product() {
        this.emails = new HashSet<>();
        this.additionalImgUrls = new HashSet<>();
    }

    @PrePersist
    @PreUpdate
    private void roundPrice() {
        if(price != null) {
            DecimalFormat df = new DecimalFormat("#.##");
            price = Double.valueOf(df.format(price));
        }
    }

    @Override
    public String toString() {
        return String.format("Project #%d\n", id) +
                String.format("Name: %s\n", name) +
                String.format("Description: %s\n", description) +
                String.format("Price: %f\n", price) +
                String.format("Width: %d\n", width) +
                String.format("Height: %d\n", height) +
                String.format("Weight: %d\n", weight) +
                String.format("Available quantity: %d", quantity);

    }

}
