package com.metauniverse.estore.order;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class MakeOrderTest {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @PostMapping("/create")
   // @PreAuthorize("isAuthenticated()")
    public String createOrder() {

        Order order;

        User user = new User();
        user.setUsername("Order Guy");
        Item item = new Item();
        Item item2 = new Item();
        Item item3 = new Item();
        item.setName("ITem 1");
        item2.setName("item 2");
        item3.setName("item 3");
        order = new Order();
        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item2);
        items.add(item3);
        order.setItems(items);
        order.setUser(user);
        order.setToCity("Датосил");
        order.setUserName(user.getUsername());
        order.setToStreet("here st");
        orderRepository.save(order);
        //     }

        return order.toString();
    }
}
