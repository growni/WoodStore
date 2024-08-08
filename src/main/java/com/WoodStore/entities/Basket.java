package com.WoodStore.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "baskets")
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private Double totalPrice;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<BasketItem> items = new HashSet<>();

    public void addItem(Product product, int quantity) {
        for (BasketItem item : items) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new BasketItem(this, product, quantity));
        setTotalPrice();
    }

    public void setTotalPrice() {
        this.totalPrice = this.getItems().stream().mapToDouble(item -> item.getProduct().getPrice()).sum();
    }

    public void removeItem(Product product) {
        items.removeIf(item -> item.getProduct().equals(product));
    }

    public void clear() {
        items.clear();
    }

    public BasketItem getItem(Product product) {
        return this.items.stream().findFirst().get();
    }
}
