package com.WoodStore.entities;

import com.WoodStore.constants.OrderStatus;
import com.WoodStore.entities.dtos.ProductDto;
import com.WoodStore.exceptions.OrderError;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

import static com.WoodStore.messages.Errors.*;

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

        if(recipient.getEmail() == null || recipient.getEmail().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_EMAIL);
        }

        if(recipient.getFirstName() == null || recipient.getFirstName().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_FIRST_NAME);
        }

        if(recipient.getLastName() == null || recipient.getLastName().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_LAST_NAME);
        }

        if(recipient.getPhoneNumber() == null || recipient.getPhoneNumber().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_PHONE);
        }
    }

    private void validateCarrier() {
        Carrier carrier = getCarrier();

        if(carrier.getRegion() == null || carrier.getRegion().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_REGION);
        }

        if(carrier.getCity() == null || carrier.getCity().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_CITY);
        }

        if(carrier.getAddress() == null || carrier.getAddress().trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_ADDRESS);
        }
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
