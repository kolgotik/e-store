package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.cart.CartRepository;
import com.metauniverse.estore.exception.email.UserNotFoundException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.util.cart_util.SessionCartBinder;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SessionCartBinderImpl implements SessionCartBinder {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private HttpSession session;

    @Override
    public void bindCartToUser(User user) {
        Cart cart = (Cart) session.getAttribute("cart");
        List<Item> cartList = cart.getItems();
        for (Item i : cartList) {
            System.out.println(i.toString());
            System.out.println(cart.getItemQuantity());
        }
        cartRepository.save(cart);
        user.setCart(cart);
    }

    @Override
    public void bindCartToUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Cart cart = (Cart) session.getAttribute("cart");
            user.get().setCart(cart);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public void bindCartToUser(OAuth2User oAuth2User) {

    }
}
