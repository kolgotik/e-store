package com.metauniverse.estore.util.cart_util;

import com.metauniverse.estore.cart.Cart;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SessionCartInitializer {

    Cart initSessionCart(HttpSession session);

}
