package com.metauniverse.estore.cart;

import jakarta.persistence.*;

@Entity
@Table(name = "users_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;


}
