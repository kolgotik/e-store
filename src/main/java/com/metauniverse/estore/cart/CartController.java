package com.metauniverse.estore.cart;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    @GetMapping
    public String showCartPage() {
        return "cart";

    }
}
