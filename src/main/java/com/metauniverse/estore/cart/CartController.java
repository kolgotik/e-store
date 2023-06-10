package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    @GetMapping
    public String showCartPage() {
        return "cart";

    }
}
