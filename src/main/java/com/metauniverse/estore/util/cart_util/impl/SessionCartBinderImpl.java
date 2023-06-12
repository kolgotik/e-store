package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.cart.CartRepository;
import com.metauniverse.estore.exception.email.UserNotFoundException;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.UserService;
import com.metauniverse.estore.util.cart_util.SessionCartBinder;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class SessionCartBinderImpl implements SessionCartBinder {

    private final UserRepository userRepository;
    private HttpSession session;

    @Override
    public void bindCartToUser(User user) {
        Cart cart = (Cart) session.getAttribute("cart");
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
