package com.WoodStore.entities;

import com.WoodStore.exceptions.OrderError;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.WoodStore.messages.errors.OrderErrors.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Recipient {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public void validate() {
        if(this.email == null || this.email.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_EMAIL);
        }

        if(this.firstName == null || this.firstName.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_FIRST_NAME);
        }

        if(this.lastName == null || this.lastName.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_LAST_NAME);
        }

        if(this.phoneNumber == null || this.phoneNumber.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_RECIPIENT_PHONE);
        }
    }
}
