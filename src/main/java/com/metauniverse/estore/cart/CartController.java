package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.UserService;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import com.metauniverse.estore.util.cart_util.CartPriceHandler;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

    private final UserRepository userRepository;


    @GetMapping
    public String showCartPage(HttpSession session, Model model, @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {
        String email = UserService.getUsernameOfAuthUser(user, oAuth2User);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User authUser = optionalUser.get();
            log.info("USER: " + authUser);
            model.addAttribute("user", authUser);
        }
        Cart cartFromSession = (Cart) session.getAttribute("cart");
        BigDecimal totalPrice = cartPriceHandler.getTotalPrice(session);
        cartFromSession.setTotalPrice(totalPrice);
        cartPriceHandler.getTotalPriceForEachItem(session);
        log.info("CART: " + cartFromSession);
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

    @GetMapping("/remove-item")
    public String removeItemFromCart(@RequestParam("itemId") Long id, HttpSession session) {
        Map<Long, Integer> itemQuantityMap = itemQuantityHandler.initSessionItemQty(session);
        log.info("INIT QTY MAP before remove: " + itemQuantityMap.toString());
        Cart cartFromSession = (Cart) session.getAttribute("cart");
        if (cartFromSession.getItems().isEmpty()) {
            items = new ArrayList<>();
        }
        List<Item> cartItemList = cartFromSession.getItems();
        Iterator<Item> itemIterator = items.iterator();
        log.info("Cart list: " + cartItemList);
        while (itemIterator.hasNext()) {
            Item item = itemIterator.next();
            if (Objects.equals(item.getId(), id)) {
                itemIterator.remove();
                itemQuantityMap.remove(id);
                Integer totalItemQuantity = itemQuantityHandler.calculateTotalItemQuantity(cartFromSession.getItems(), id, session);
                BigDecimal totalPrice = cartPriceHandler.getTotalPrice(session);
                cartFromSession.setItemQuantity(totalItemQuantity);
                cartFromSession.setTotalPrice(totalPrice);
                log.info("INIT QTY MAP after remove: " + itemQuantityMap.toString());
            }
        }
        cartFromSession.setQtyOfEachItem(itemQuantityMap);
        cartFromSession.setItems(items);
        log.info("FINAL SESSION CART: " + cartFromSession);
        return "redirect:/cart";
    }
}
