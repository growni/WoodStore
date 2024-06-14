//package com.WoodStore.entities;
//
//import com.WoodStore.constants.OrderStatus;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//@Getter
//@Setter
//@Entity
//@AllArgsConstructor
//@Table(name = "orders")
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "order_date", nullable = false)
//    private Date orderDate;
//
//    @Column(nullable = false)
//    private OrderStatus status;
//
//    public Order() {
//        this.products = new HashSet<>();
//    }
//
//    @ManyToMany
//    @JoinTable(
//            name = "order_product",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    private Set<Product> products;
//}
