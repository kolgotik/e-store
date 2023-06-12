package com.metauniverse.estore.util.cart_util;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.item.CartItem;
import com.metauniverse.estore.item.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface CartItemQuantityHandler {

    Map<Long, Integer> initSessionQuantityMap(HttpSession session);

    Integer calculateItemQuantity(/*ItemService itemService,*/ List<CartItem> items, Long id, Map<Long, Integer> map, Cart cart, Integer selectedQuantity, Model model, HttpSession session);

}
