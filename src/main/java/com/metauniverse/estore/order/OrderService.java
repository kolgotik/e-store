package com.metauniverse.estore.order;


public interface OrderService {
    void placeOrderForUser(String username, Order order);

}
