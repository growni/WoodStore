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
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonManagedReference
    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BasketItem> basketItems;

    public void addItem(Product product, Integer quantity) {
        BasketItem basketItem = new BasketItem(product, this, quantity);
        basketItems.add(basketItem);
    }

    public Basket() {
        this.basketItems = new HashSet<>();
    }

    public void removeItem(BasketItem basketItem) {
        basketItems.remove(basketItem);
    }

    public Double getTotalPrice() {
        return basketItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
