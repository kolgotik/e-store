package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import com.metauniverse.estore.util.cart_util.CartPriceHandler;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final ItemService itemService;
    private List<Item> items;
    private final SessionCartInitializer cartInitializer;
    private final CartItemQuantityHandler itemQuantityHandler;
    private final CartPriceHandler cartPriceHandler;


    @GetMapping
    public String showCartPage(HttpSession session) {
        Cart cartFromSession = (Cart) session.getAttribute("cart");
        BigDecimal totalPrice = cartPriceHandler.getTotalPrice(session);
        cartFromSession.setTotalPrice(totalPrice);
        cartPriceHandler.getTotalPriceForEachItem(session);
        List<Item> cartItems = cartFromSession.getItems();
        for (Item item : cartItems) {
            log.info("Item sfd: " + item.getName() + " ");
        }
        log.info("ITEM LIST SESSION: " + cartItems);
        return "cart";

    }

    @GetMapping("/add-item")
    public String addItemToCart(@RequestParam("itemId") Long id, @RequestParam("qty") Integer selectedQuantity, Model model, HttpSession session) {
        Integer factualQuantity = itemService.getQuantityOfItem(id);
        Cart cart = cartInitializer.initSessionCart(session);
        if (cart.getItems().isEmpty()) {
            items = new ArrayList<>();
        }
        Map<Long, Integer> itemQuantityMap = itemQuantityHandler.initSessionItemQty(session);
        log.info("INIT QTY MAP: " + itemQuantityMap.toString());
        Optional<Item> item = itemService.getItemById(id);
        if (!itemQuantityHandler.isItemAlreadyAdded(id, model)) {
            if (item.isPresent()) {
                if (selectedQuantity > factualQuantity) {
                    selectedQuantity = factualQuantity;
                }
                Item itemForCart = item.get();
                items.add(itemForCart);
                itemQuantityMap.put(itemForCart.getId(), selectedQuantity);
                cart.setItems(items);
                Integer totalItemQuantity = itemQuantityHandler.calculateTotalItemQuantity(cart.getItems(), id, session);
                BigDecimal totalPrice = cartPriceHandler.getTotalPrice(session);
                log.info("ITEM QTY: " + totalItemQuantity);
                cart.setItemQuantity(totalItemQuantity);
                cart.setQtyOfEachItem(itemQuantityMap);
                cart.setTotalPrice(totalPrice);
            }
        }
        log.info("SESSION CART: " + cart.toString());
        log.info("SESSION CART LIST: " + cart.getItems());
        log.info("QTY MAP: " + itemQuantityMap.toString());
        log.info("SESSION ID: " + session.getId());
        itemService.defineItemAvailability(id, model);
        model.addAttribute("item", item);
        return "redirect:/item/get-item?itemId=" + id;
    }
}
