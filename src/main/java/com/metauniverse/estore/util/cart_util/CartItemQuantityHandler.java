package com.metauniverse.estore.util.cart_util;

import com.metauniverse.estore.item.ItemDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface CartItemQuantityHandler {

    Map<Long, Integer> initSessionItemQty(HttpSession session);
    Integer calculateItemQuantity(List<ItemDTO> items, Long id, Integer selectedQuantity);
    boolean isItemAlreadyAdded(Long id, Model model);

}
