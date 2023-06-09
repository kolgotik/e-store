package com.metauniverse.estore.util.cart_util;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface CartItemQuantityHandler {

    Map<Long, Integer> initSessionItemQty(HttpSession session);

    Integer calculateItemQuantity(List<Item> sessionCartItems, Long id, Integer selectedQuantity, HttpSession session);
    Integer calculateTotalItemQuantity(List<Item> sessionCartItems, Long id, HttpSession session);

    boolean isItemAlreadyAdded(Long id, Model model);

}
