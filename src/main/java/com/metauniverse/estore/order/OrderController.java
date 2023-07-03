package com.metauniverse.estore.order;

import com.metauniverse.estore.exception.order.NotEnoughBalanceException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemService;
import com.metauniverse.estore.item.controller.CategoryController;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserService;
import com.metauniverse.estore.util.s3_util.util.AmazonS3Initializer;
import com.metauniverse.estore.util.s3_util.util.S3BucketDataManager;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private Map<String, List<String>> countryCityMap;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private Map<Long, String> itemsImgLinks;
    private ItemService itemService;

    private void initializeCountryCityMap() {
        Locale[] locales = Locale.getAvailableLocales();
        countryCityMap = Arrays.stream(locales)
                .collect(Collectors.groupingBy(
                        Locale::getDisplayCountry,
                        Collectors.mapping(Locale::getDisplayVariant, Collectors.toList())
                ));

        // Sort cities within each country
        countryCityMap.values().forEach(cities -> cities.sort(Comparator.naturalOrder()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/checkout-page")
    public String showCheckoutPage(Model model) {
        initializeCountryCityMap();
        Order order = new Order();
        Locale[] locales = Locale.getAvailableLocales();
        List<String> countries = Arrays.stream(locales)
                .map(locale -> new Locale("", locale.getCountry()).getDisplayCountry(Locale.ENGLISH))
                .distinct()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        model.addAttribute("countries", countries);
        model.addAttribute("order", order);
        return "checkout";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/process-checkout")
    public String processCheckout(@ModelAttribute("order") Order order, @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {
        String username = UserService.getUsernameOfAuthUser(user, oAuth2User);
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDateTime = dateTime.format(formatter);
            order.setDateOfOrderPlacement(formattedDateTime);
            orderService.placeOrderForUser(username, order);
        } catch (NotEnoughBalanceException e) {
            log.error(e.getMessage());
            return "order-no-balance-err";
        }
        orderService.cleanCartAfterOrderPlacement();
        return "order-success";
    }

    @GetMapping("/view-order")
    public String viewOrders(@RequestParam("orderId") Long id, Model model) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            List<Item> orderItems = order.get().getItems();
            itemService.setImageLinksIntoSession(orderItems);
            model.addAttribute("order", order.get());
            model.addAttribute("orderItems", orderItems);
            model.addAttribute("itemsImgLinks", itemsImgLinks);
        }

        return "view-order";
    }
}
