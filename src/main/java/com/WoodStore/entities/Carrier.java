package com.WoodStore.entities;

import com.WoodStore.exceptions.OrderError;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.WoodStore.messages.Errors.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Carrier {
    private String region;
    private String city;
    private String address;

    public void validate() {
        if(this.region == null || this.region.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_REGION);
        }

        if(this.city == null || this.city.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_CITY);
        }

        if(this.address == null || this.address.trim().isEmpty()) {
            throw new OrderError(INVALID_ORDER_CARRIER_ADDRESS);
        }
    }
}
