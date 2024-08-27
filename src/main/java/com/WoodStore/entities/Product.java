package com.WoodStore.entities;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.exceptions.EmailError;
import com.WoodStore.exceptions.ProductPropertyError;
import com.WoodStore.utils.SetToStringConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static com.WoodStore.constants.Constants.*;
import static com.WoodStore.messages.errors.ProductErrors.*;
import static com.WoodStore.messages.errors.OrderErrors.*;

@Getter
@Entity
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

    public Product(Long id, String description, String name, Double price, Integer width, Integer height, Integer weight, Integer quantity, String imageUrl, ProductMaterial material, ProductCategory category, Set<String> additionalImgUrls, Set<String> emails) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.width = width;
        this.height = height;
        this.weight = weight;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
        this.material = material;
        this.category = category;
        this.additionalImgUrls = additionalImgUrls;
        this.emails = emails;
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

    public void validate() {
        validateName();
        validateDescription();
        validatePrice();
        validateWidth();
        validateHeight();
        validateWeight();
        validateQuantity();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;

        validateDescription();
    }

    public void setName(String name) {
        this.name = name;

        validateName();
    }

    public void setPrice(Double price) {
        this.price = price;

        validatePrice();
    }

    public void setWidth(Integer width) {
        this.width = width;

        validateWidth();
    }

    public void setHeight(Integer height) {
        this.height = height;

        validateHeight();
    }

    public void setWeight(Integer weight) {
        this.weight = weight;

        validateWeight();
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;

        validateQuantity();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;

        validateImageUrl(imageUrl);
    }

    public void setMaterial(ProductMaterial material) {
        this.material = material;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void addAdditionalImage(String imageUrl) {
        this.additionalImgUrls.add(imageUrl);

        validateImageUrl(imageUrl);
    }

    public void setAdditionalImgUrls(Set<String> additionalImgUrls) {
        this.additionalImgUrls = additionalImgUrls;

        for (String url : additionalImgUrls) {
            validateImageUrl(url);
        }
    }

    public void addEmail(String email) {
        this.emails.add(email);

        validateEmail(email);
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;

        for (String email : emails) {
            validateEmail(email);
        }
    }

    public void validateEmail(String email) {
        String emailRegexPattern = "^(.+)@(\\S+)$";
        boolean isValidEmail = Pattern.compile(emailRegexPattern).matcher(email).matches();

        if(!isValidEmail) {
            throw new EmailError(INVALID_EMAIL_ERROR);
        }

    }

    public void validateName() {
        if(name == null || name.trim().length() < PRODUCT_NAME_MIN_LENGTH || name.trim().length() > PRODUCT_NAME_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_NAME_LENGTH_ERROR);
        }
    }

    public void validateDescription() {
        if(description.trim().length() > PRODUCT_DESCRIPTION_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_DESCRIPTION_LENGTH_ERROR);
        }
    }

    public void validatePrice() {
        if(price == null || price.isNaN() || price < PRODUCT_PRICE_MIN || price > PRODUCT_PRICE_MAX) {
            throw new ProductPropertyError(PRODUCT_PRICE_ERROR);
        }
    }

    public void validateWidth() {
        if(width < PRODUCT_WIDTH_MIN || width > PRODUCT_WIDTH_MAX) {
            throw new ProductPropertyError(PRODUCT_WIDTH_ERROR);
        }
    }

    public void validateHeight() {
        if(height < PRODUCT_HEIGHT_MIN || height > PRODUCT_HEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_HEIGHT_ERROR);
        }
    }

    public void validateWeight() {
        if(weight < PRODUCT_WEIGHT_MIN || weight > PRODUCT_WEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_WEIGHT_ERROR);
        }
    }

    public void validateQuantity() {
        if(quantity == null || quantity < PRODUCT_QUANTITY_MIN || quantity > PRODUCT_QUANTITY_MAX) {
            throw new ProductPropertyError(PRODUCT_QUANTITY_ERROR);
        }
    }

    public void validateImageUrl(String url) {
        String emailRegexPattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        boolean isValidUrl = Pattern.compile(emailRegexPattern).matcher(url).matches();

        if(!isValidUrl) {
            throw new ProductPropertyError(INVALID_IMAGE_URL);
        }
    }

    //Used to properly compare different instances of the Product class
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
