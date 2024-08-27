package com.WoodStore.entities;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.exceptions.OrderError;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.WoodStore.messages.errors.OrderErrors.*;

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

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    private Recipient recipient;

    @Embedded
    private Carrier carrier;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id")
    private Basket basket;


    public Order() {
        this.orderDate = LocalDate.now();
        this.status = OrderStatus.PROCESSING;
    }

    private void validateRecipient() {
        Recipient recipient = getRecipient();

        if(recipient == null) {
            throw new OrderError(INVALID_ORDER_RECIPIENT);
        }

        recipient.validate();
    }

    private void validateCarrier() {
        Carrier carrier = getCarrier();

        if(carrier == null) {
            throw new OrderError(INVALID_ORDER_CARRIER);
        }

        carrier.validate();
    }

    private void validateBasket() {
        Basket basket = getBasket();

        if(basket == null) {
            throw new OrderError(INVALID_ORDER_BASKET);
        }
    }

    public void validate() {
        validateRecipient();
        validateCarrier();
        validateBasket();
    }

}
