package com.WoodStore.entities;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.dtos.ProductDto;
import jakarta.persistence.*;
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

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;


    public Order() {
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PROCESSING;
    }

}
