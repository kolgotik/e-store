package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.item.CartItem;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class CartItemQuantityHandlerImpl implements CartItemQuantityHandler {

    private final ItemService itemService;
    @Override
    public Map<Long, Integer> initSessionQuantityMap(HttpSession session) {
        Map<Long, Integer> itemQuantityMap = (Map<Long, Integer>) session.getAttribute("itemsQty");
        if (itemQuantityMap == null) {
            itemQuantityMap = new HashMap<>();
            session.setAttribute("itemsQty", itemQuantityMap);
        }
        return itemQuantityMap;
    }

    @Override
    public Integer calculateItemQuantity(/*ItemService itemService, */List<CartItem> items, Long id, Map<Long, Integer> map, Cart cart, Integer selectedQuantity, Model model, HttpSession session) {
        Optional<Item> item = itemService.getItemById(id);
        Integer totalQuantity = null;
        if (item.isPresent()) {
            Item itemForCart = item.get();
            CartItem cartItem = new CartItem(itemForCart);
            items.add(cartItem);
            cartItem.setQuantity(selectedQuantity);
            map.put(itemForCart.getId(), selectedQuantity);
            cart.setCartItemList(items);
            for (CartItem i : items) {
                log.info("ITEM: " + "   " + i.getName() + i.getClass().getName());
            }
            log.info("ITEM QUANTITY: " + "   " + map.toString());
            totalQuantity = items.stream().mapToInt(CartItem::getQuantity).sum();
            session.setAttribute("cartItems", items);
            //model.addAttribute("item", item.get());
            itemService.defineItemAvailability(id, model);

        }
        return totalQuantity;
    }
}
