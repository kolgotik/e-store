package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemDTO;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.item.enums.ItemAvailability;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.*;

@Component
@Slf4j
@AllArgsConstructor
public class CartItemQuantityHandlerImpl implements CartItemQuantityHandler {

    private final ItemService itemService;
    private final HttpSession session;

    @Override
    public Map<Long, Integer> initSessionItemQty(HttpSession session) {
        Map<Long, Integer> itemQuantityMap = (Map<Long, Integer>) session.getAttribute("itemsQty");
        if (itemQuantityMap == null) {
            itemQuantityMap = new HashMap<>();
            session.setAttribute("itemsQty", itemQuantityMap);
        }
        return itemQuantityMap;
    }

    @Override
    public Integer calculateItemQuantity(Long id, Integer selectedQuantity) {
        Optional<Item> item = itemService.getItemById(id);
        List<ItemDTO> items = new ArrayList<>();
        Integer totalQuantity = null;
        if (item.isPresent()) {
            Item itemForCart = item.get();
            ItemDTO cartItem = new ItemDTO(itemForCart);
            items.add(cartItem);
            cartItem.setQuantity(selectedQuantity);
            for (ItemDTO i : items) {
                log.info("ITEM: " + "   " + i.getName() + i.getClass().getName());
            }
            totalQuantity = items.stream().mapToInt(ItemDTO::getQuantity).sum();
        }
        return totalQuantity;
    }

    @Override
    public Integer calculateItemQuantity(List<ItemDTO> items, Long id, Integer selectedQuantity) {
        Optional<Item> item = itemService.getItemById(id);
        Integer totalQuantity = null;
        if (item.isPresent()) {
            Item itemForCart = item.get();
            ItemDTO cartItem = new ItemDTO(itemForCart);
            items.add(cartItem);
            cartItem.setQuantity(selectedQuantity);
            for (ItemDTO i : items) {
                log.info("ITEM: " + "   " + i.getName() + i.getClass().getName());
            }
            totalQuantity = items.stream().mapToInt(ItemDTO::getQuantity).sum();
        }
        return totalQuantity;
    }

    @Override
    public boolean isItemAlreadyAdded(Long id, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        List<Item> cartItemList = cart.getItems();
        for (Item i : cartItemList) {
            if (i.getId().equals(id)) {
                model.addAttribute("itemAlreadyAddedMSG", ItemAvailability.ITEM_ALREADY_ADDED.getValue());
                log.error("ITEM ALREADY ADDED: " + id);
                return true;
            }
        }
        return false;
    }
}
