package com.WoodStore.entities.dtos;

import com.WoodStore.entities.Order;
import com.WoodStore.entities.OrderProductKey;
import com.WoodStore.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("orderId")
    @JoinColumn(name = "oder_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    public OrderProduct(OrderProductKey id, Order order, Product product) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = 1;
    }
}
