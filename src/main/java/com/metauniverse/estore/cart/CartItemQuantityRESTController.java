package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
@RequestMapping("/cart")
@AllArgsConstructor
public class CartItemQuantityRESTController {

    private final CartItemQuantityHandler quantityHandler;
    private final ItemService itemService;

    @PostMapping("/change-quantity")
    public ResponseEntity<String> manageItemQuantity(@RequestBody Map<String, String> changeQtyRequest, HttpSession session) {
        Map<Long, Integer> itemQuantityMap = (Map<Long, Integer>) session.getAttribute("itemsQty");
        Cart cart = (Cart) session.getAttribute("cart");
        List<Item> itemList = cart.getItems();
        if (itemQuantityMap == null) {
            itemQuantityMap = new HashMap<>();
            session.setAttribute("itemsQty", itemQuantityMap);
        }
        Long itemId = Long.valueOf(changeQtyRequest.get("itemId"));
        Optional<Item> item = itemService.getItemById(itemId);

        Integer factualQuantity = itemService.getQuantityOfItem(itemId);
        Integer selectedQuantity = Integer.valueOf(changeQtyRequest.get("quantity"));
        if (selectedQuantity > factualQuantity) {
            selectedQuantity = factualQuantity;
        }
        if (selectedQuantity < 1) {
            selectedQuantity = 1;
        }
        log.info("id " + itemId);
        log.info("qty " + selectedQuantity);
        if (itemQuantityMap.containsKey(itemId)) {
            itemQuantityMap.put(itemId, selectedQuantity);
        }
        log.info("MAP: " + cart.getQtyOfEachItem());
        log.info("SESSION MAP: " + itemQuantityMap);
        Integer totalQuantity = null;
        if (item.isPresent()) {
            if (itemList.contains(item.get())) {
                totalQuantity = quantityHandler.calculateTotalItemQuantity(itemList, itemId, session);
                log.info("TOTAL QTY: " + totalQuantity);
            }
        }
        cart.setItemQuantity(totalQuantity);
        return ResponseEntity.ok().build();
    }
}
