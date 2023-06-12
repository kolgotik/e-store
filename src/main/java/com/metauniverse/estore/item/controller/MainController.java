package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.cart.CartRepository;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class MainController {

    private final SessionCartInitializer cartInitializer;
    private final CartRepository cartRepository;
    @GetMapping
    public String mainPage(HttpSession session) {
        Cart cart = cartInitializer.initSessionCart(session);
        cartRepository.save(cart);

        return "index";
    }

}
