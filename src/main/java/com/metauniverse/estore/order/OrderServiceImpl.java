package com.metauniverse.estore.order;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.exception.email.UserNotFoundException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private HttpSession session;

    public static String getUsernameOfAuthUser(User user, OAuth2User oAuth2User) {
        String username = "";
        try {
            if (oAuth2User != null) {
                username = oAuth2User.getAttribute("email");
            } else if (user != null) {
                username = user.getEmail();
            }

        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }
        return username;
    }

    @Override
    public void placeOrderForUser(String username, Order order) {
        Optional<User> userFromDb = userRepository.findByEmail(username);
        Cart cart = (Cart) session.getAttribute("cart");
        List<Item> cartItems = cart.getItems();
        List<Long> itemsIds = cartItems.stream()
                .map(Item::getId)
                .toList();
        List<Item> itemsFromDb = (List<Item>) itemRepository.findAllById(itemsIds);
        log.info("ITEMS FROM DB: " + itemsFromDb);
        order.setItems(itemsFromDb);
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            List<Order> orders = user.getOrders();
            log.info("ORDERS BEFORE ADDING NEW ORDER: " + orders);
            orders.add(order);
            log.info("ORDER: " + order);
            log.info("ORDERS AFTER ADDING NEW ORDER: " + orders);
            user.setOrders(orders);
            order.setUser(user);
            userRepository.save(user);
            orderRepository.save(order);
        }
    }
}
