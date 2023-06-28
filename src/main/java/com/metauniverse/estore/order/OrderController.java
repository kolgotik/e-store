package com.metauniverse.estore.order;

import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private Map<String, List<String>> countryCityMap;
    private final OrderService orderService;

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
        String username = OrderServiceImpl.getUsernameOfAuthUser(user, oAuth2User);
        orderService.placeOrderForUser(username, order);
        orderService.cleanCartAfterOrderPlacement();
        return "order-success";
    }
}
