package com.metauniverse.estore.order;

import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    @GetMapping("/checkout-page")
    public String showCheckoutPage(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        return "checkout";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/process-checkout")
    public String processCheckout(@ModelAttribute("order") Order order, @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {
        return "";
    }
}
