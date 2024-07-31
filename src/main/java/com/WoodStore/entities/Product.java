package com.WoodStore.entities;

import com.WoodStore.constants.ProductCategory;
import com.WoodStore.constants.ProductMaterial;
import com.WoodStore.exceptions.EmailError;
import com.WoodStore.exceptions.ProductPropertyError;
import com.WoodStore.utils.SetToStringConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static com.WoodStore.constants.Constants.*;
import static com.WoodStore.messages.Errors.*;
import static com.WoodStore.messages.Errors.INVALID_IMAGE_URL;

@Getter
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

    public void validate() {
        validateName(this.getName());
        validateDescription(this.getDescription());
        validatePrice(this.getPrice());
        validateWidth(this.getWidth());
        validateHeight(this.getHeight());
        validateWeight(this.getWeight());
        validateQuantity(this.getQuantity());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        validateDescription(description);

        this.description = description;
    }

    public void setName(String name) {
        validateName(name);

        this.name = name;
    }

    public void setPrice(Double price) {
        validatePrice(price);

        this.price = price;
    }

    public void setWidth(Integer width) {
        validateWidth(width);

        this.width = width;
    }

    public void setHeight(Integer height) {
        validateHeight(height);

        this.height = height;
    }

    public void setWeight(Integer weight) {
        validateWeight(weight);

        this.weight = weight;
    }

    public void setQuantity(Integer quantity) {
        validateQuantity(quantity);

        this.quantity = quantity;
    }

    public void setImageUrl(String imageUrl) {
        validateImageUrl(imageUrl);

        this.imageUrl = imageUrl;
    }

    public void setMaterial(ProductMaterial material) {
        this.material = material;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public void addAdditionalImage(String imageUrl) {
        validateImageUrl(imageUrl);
        this.additionalImgUrls.add(imageUrl);
    }

    public void setAdditionalImgUrls(Set<String> additionalImgUrls) {
        for (String url : additionalImgUrls) {
            validateImageUrl(url);
        }

        this.additionalImgUrls = additionalImgUrls;
    }

    public void addEmail(String email) {
        validateEmail(email);

        this.emails.add(email);
    }

    public void setEmails(Set<String> emails) {
        for (String email : emails) {
            validateEmail(email);
        }

        this.emails = emails;
    }

    private void validateEmail(String email) {
        String emailRegexPattern = "^(.+)@(\\S+)$";
        boolean isValidEmail = Pattern.compile(emailRegexPattern).matcher(email).matches();

        if(!isValidEmail) {
            throw new EmailError(INVALID_EMAIL_ERROR);
        }

    }

    private void validateName(String name) {
        if(name == null || name.trim().length() == PRODUCT_NAME_MIN_LENGTH || name.trim().length() > PRODUCT_NAME_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_NAME_LENGTH_ERROR);
        }
    }

    private void validateDescription(String description) {
        if(description.trim().length() > PRODUCT_DESCRIPTION_MAX_LENGTH) {
            throw new ProductPropertyError(PRODUCT_DESCRIPTION_LENGTH_ERROR);
        }
    }

    private void validatePrice(Double price) {
        if(price == null || price.isNaN() || price < PRODUCT_PRICE_MIN || price > PRODUCT_PRICE_MAX) {
            throw new ProductPropertyError(PRODUCT_PRICE_ERROR);
        }
    }

    private void validateWidth(Integer width) {
        if(width < PRODUCT_WIDTH_MIN || width > PRODUCT_WIDTH_MAX) {
            throw new ProductPropertyError(PRODUCT_WIDTH_ERROR);
        }
    }

    private void validateHeight(Integer height) {
        if(height < PRODUCT_HEIGHT_MIN || height > PRODUCT_HEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_HEIGHT_ERROR);
        }
    }

    private void validateWeight(Integer weight) {
        if(weight < PRODUCT_WEIGHT_MIN || weight > PRODUCT_WEIGHT_MAX) {
            throw new ProductPropertyError(PRODUCT_WEIGHT_ERROR);
        }
    }

    private void validateQuantity(Integer quantity) {
        if(quantity == null || quantity < PRODUCT_QUANTITY_MIN || quantity > PRODUCT_QUANTITY_MAX) {
            throw new ProductPropertyError(PRODUCT_QUANTITY_ERROR);
        }
    }

    private void validateImageUrl(String url) {
        String emailRegexPattern = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
        boolean isValidUrl = Pattern.compile(emailRegexPattern).matcher(url).matches();

        if(!isValidUrl) {
            throw new ProductPropertyError(INVALID_IMAGE_URL);
        }
    }

}
