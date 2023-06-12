package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.CartItem;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final CartService cartService;
    private final ItemService itemService;
    private final UserRepository userRepository;
    private List<Item> items;
    private List<CartItem> items2;
    private final SessionCartInitializer cartInitializer;
    private final CartItemQuantityHandler itemQuantityHandler;


    @GetMapping
    public String showCartPage(HttpSession session) {
        Cart cartFromSession = (Cart) session.getAttribute("cart");
        List<Item> cartItems = cartFromSession.getItems();
        for (Item item : cartItems) {
            log.info("Item: " + item.getName() + " ");
        }
        return "cart";

    }

    @GetMapping("/add-item")
    public String addItemToCart(@RequestParam("itemId") Long id, @RequestParam("qty") Integer selectedQuantity, Model model, HttpSession session) {

        Cart cart = cartInitializer.initSessionCart(session);
        Map<Long, Integer> itemQuantityMap = itemQuantityHandler.initSessionQuantityMap(session);

        Optional<Item> item = itemService.getItemById(id);
       if (item.isPresent()) {
            Item itemForCart = item.get();
            items.add(itemForCart);
            itemQuantityMap.put(itemForCart.getId(), selectedQuantity);
            cart.setItems(items);
        }
        cart.setItemQuantity(items.size());
        itemService.defineItemAvailability(id, model);
        model.addAttribute("item", item);
        return "redirect:/item/get-item?itemId=" + id;
    }
}
