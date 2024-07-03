package com.WoodStore.entities;

import com.WoodStore.entities.dtos.ProductDto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "baskets")
@Component
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BasketItem> basketItems;

    private String email;
    private Double totalPrice;

    public void addItem(Product product, Integer quantity) {
        BasketItem basketItem = new BasketItem(product, this, quantity);
        basketItems.add(basketItem);
    }

    public Basket() {
        this.basketItems = new HashSet<>();
        setTotalPrice();
    }

    public void removeItem(BasketItem basketItem) {
        basketItems.remove(basketItem);
    }

    public void setTotalPrice() {
        this.totalPrice =  basketItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
