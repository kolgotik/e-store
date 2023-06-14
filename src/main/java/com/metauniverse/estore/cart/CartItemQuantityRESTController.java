package com.metauniverse.estore.cart;

import com.metauniverse.estore.util.cart_util.CartItemQuantityHandler;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/cart")
@AllArgsConstructor
public class CartItemQuantityRESTController {

    private final CartItemQuantityHandler quantityHandler;
    @PostMapping("/change-quantity")
    public ResponseEntity<String> manageItemQuantity(@RequestBody Map<String, String> request, HttpSession session) {
        Map<Long, Integer> itemQuantityMap = (Map<Long, Integer>) session.getAttribute("itemsQty");
        if (itemQuantityMap == null) {
            itemQuantityMap = new HashMap<>();
            session.setAttribute("itemsQty", itemQuantityMap);
        }
        String id = (String) request.get("itemId");
        Long itemId = Long.valueOf(id);
        Integer quantity = Integer.valueOf(request.get("quantity"));
        log.info("id " + itemId);
        log.info("qty " + quantity);
        if (itemQuantityMap.containsKey(itemId)) {
            itemQuantityMap.put(itemId, quantity);
        }
       // session.setAttribute("itemsQty", itemQuantityMap);
        return ResponseEntity.ok().build();
    }
}
