package com.metauniverse.estore.order;


import com.metauniverse.estore.cart.Cart;

import java.util.Map;

public interface OrderService {
    void placeOrderForUser(String username, Order order);
    void cleanCartAfterOrderPlacement();

}
