package com.metauniverse.estore.util.cart_util;

import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

public interface CartPriceHandler {

    BigDecimal getTotalPrice(HttpSession session);

    void getTotalPriceForEachItem(HttpSession session);

}
