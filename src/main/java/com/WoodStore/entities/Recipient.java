package com.WoodStore.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
}
