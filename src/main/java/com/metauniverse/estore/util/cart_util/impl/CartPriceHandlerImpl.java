package com.metauniverse.estore.util.cart_util.impl;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.util.cart_util.CartPriceHandler;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class CartPriceHandlerImpl implements CartPriceHandler {

    @Override
    public BigDecimal getTotalPrice(HttpSession session) {
        BigDecimal totalPrice = BigDecimal.valueOf(0);
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Long, Integer> itemsQty = (Map<Long, Integer>) session.getAttribute("itemsQty");
        List<Item> itemList = cart.getItems();
        if (!itemList.isEmpty()) {
            for (Item item : itemList) {
                Integer quantityOfItem = itemsQty.get(item.getId());
                BigDecimal priceOfItem = item.getPrice().multiply(BigDecimal.valueOf(quantityOfItem));
                totalPrice = totalPrice.add(priceOfItem);
            }
        }
        return totalPrice;
    }

    @Override
    public void getTotalPriceForEachItem(HttpSession session) {
        Map<Long, BigDecimal> totalPriceOfEachItem = (Map<Long, BigDecimal>) session.getAttribute("totalPriceOfEachItem");
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Long, Integer> itemsQty = (Map<Long, Integer>) session.getAttribute("itemsQty");
        List<Item> itemList = cart.getItems();

        if (totalPriceOfEachItem == null) {
            totalPriceOfEachItem = new HashMap<>();
        }

        if (!itemList.isEmpty()) {
            for (Item item : itemList) {
                Integer quantityOfItem = itemsQty.get(item.getId());
                BigDecimal priceOfItem = item.getPrice().multiply(BigDecimal.valueOf(quantityOfItem));
                totalPriceOfEachItem.put(item.getId(), priceOfItem);
            }
        }
        session.setAttribute("totalPriceOfEachItem", totalPriceOfEachItem);
    }
}
