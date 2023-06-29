package com.metauniverse.estore.order;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.exception.order.NotEnoughBalanceException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private HttpSession session;

    public boolean doesUserHaveBalance(User user, BigDecimal orderTotalPrice) {
        if (user.getBalance() != null) {
            return user.getBalance().compareTo(orderTotalPrice) >= 0;
        }
        return false;
    }

    private BigDecimal deductFromBalance(BigDecimal totalPrice, User user) {
        BigDecimal userBalance = user.getBalance();
        userBalance = userBalance.subtract(totalPrice);
        return userBalance;
    }

    /*public static List<Order> formatUsersOrdersList(User user) {
        List<Order> formattedOrders = new ArrayList<>();
        List<Order> orders = user.getOrders();
        for (Order order : orders) {
            Order formattedOrder = new Order();
            formattedOrder.setUser(order.getUser());
            formattedOrder.setId(order.getId());
            formattedOrder.
        }
    }*/
    @Override
    public void placeOrderForUser(String username, Order order) {
        Optional<User> userFromDb = userRepository.findByEmail(username);
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Long, Integer> itemsQty = new HashMap<>(cart.getQtyOfEachItem());
        List<Item> cartItems = cart.getItems();
        List<Long> itemsIds = cartItems.stream()
                .map(Item::getId)
                .toList();
        List<Item> itemsFromDb = (List<Item>) itemRepository.findAllById(itemsIds);
        log.info("ITEMS FROM DB: " + itemsFromDb);
        order.setItems(itemsFromDb);
        order.setItemQuantity(itemsQty);
        order.setTotalQuantity(cart.getItemQuantity());
        order.setTotalPrice(cart.getTotalPrice());
        order.setUniqueId(UUID.randomUUID());
        if (userFromDb.isPresent()) {
            User user = userFromDb.get();
            if (doesUserHaveBalance(user, cart.getTotalPrice())) {
                BigDecimal remainingBalance = deductFromBalance(cart.getTotalPrice(), user);
                List<Order> orders = user.getOrders();
                orders.add(order);
                log.info("ORDER: " + order);
                user.setOrders(orders);
                user.setBalance(remainingBalance);
                order.setUser(user);
                userRepository.save(user);
                orderRepository.save(order);
            } else {
                throw new NotEnoughBalanceException();
            }
        }
    }

    @Override
    public void cleanCartAfterOrderPlacement() {
        Cart cart = (Cart) session.getAttribute("cart");
        Map<Long, Integer> itemsQty = (Map<Long, Integer>) session.getAttribute("itemsQty");
        itemsQty.clear();
        cart.setTotalPrice(BigDecimal.valueOf(0));
        cart.setItemQuantity(0);
        cart.setQtyOfEachItem(new HashMap<>());
        cart.setItems(new ArrayList<>());
    }
}
