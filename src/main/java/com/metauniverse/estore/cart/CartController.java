package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemDTO;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.item.ItemServiceImpl;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import com.metauniverse.estore.util.cart_util.CartPriceHandler;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
@Slf4j
public class CartController {

    private final ItemService itemService;
    private List<Item> items;
    private List<ItemDTO> itemDTOS;
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
        return "cart";

    }
    @GetMapping("/add-item")
    public String addItemToCart(@RequestParam("itemId") Long id, @RequestParam("qty") Integer selectedQuantity, Model model, HttpSession session) {

        Cart cart = cartInitializer.initSessionCart(session);
        Map<Long, Integer> itemQuantityMap = itemQuantityHandler.initSessionItemQty(session);
        Optional<Item> item = itemService.getItemById(id);
        if (!itemQuantityHandler.isItemAlreadyAdded(id, model)) {
            if (item.isPresent()) {
                Integer itemQuantity = itemQuantityHandler.calculateItemQuantity(itemDTOS, id, selectedQuantity);
                Item itemForCart = item.get();
                items.add(itemForCart);
                itemQuantityMap.put(itemForCart.getId(), selectedQuantity);
                cart.setItems(items);
                cart.setItemQuantity(itemQuantity);
                cart.setQtyOfEachItem(itemQuantityMap);
            }
        }
        itemService.defineItemAvailability(id, model);
        model.addAttribute("item", item);
        return "redirect:/item/get-item?itemId=" + id;
    }
    @PostMapping("/quantity")
    public String manageItemQuantity(@RequestParam("quantity") Integer quantity) {
        log.info("SELECTED QUANTITY: " + quantity);
        return "redirect:/cart";
    }
}
