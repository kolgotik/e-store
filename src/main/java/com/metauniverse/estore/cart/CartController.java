package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
    @GetMapping
    public String showCartPage() {
        return "cart";

    }

    @GetMapping("/add-item")
    public String addItemToCart(@RequestParam("itemId") Long id, Model model, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        Optional<Item> item = itemService.getItemById(id);
        if (item.isPresent()) {
                items.add(item.get());
                cart.setItems(items);
                for (Item i : items) {
                    log.info("ITEM: " + i.getName());
                }
        }
        cart.setItemQuantity(items.size());
        itemService.defineItemAvailability(id, model);
        model.addAttribute("item", item);
        return "redirect:/item/get-item?itemId=" + id;
    }
}
