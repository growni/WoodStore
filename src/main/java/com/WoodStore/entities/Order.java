package com.WoodStore.entities;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.dtos.OrderProduct;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderProduct> orderProducts;

    public Order() {
        this.orderProducts = new HashSet<>();
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PROCESSING;
    }

}
