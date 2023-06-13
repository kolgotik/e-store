package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.cart.CartRepository;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SessionCartInitializerImpl implements SessionCartInitializer {

    @Override
    public Cart initSessionCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
           // cartRepository.save(cart);
        }
        return cart;
    }
}
