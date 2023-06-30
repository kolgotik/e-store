package com.metauniverse.estore.user;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public String showProfilePage(Model model, @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {
        String email = UserService.getUsernameOfAuthUser(user, oAuth2User);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User authUser = optionalUser.get();
            model.addAttribute("user", authUser);
        }
        return "profile";
    }

    @PostMapping("/add-balance")
    public String topUpBalance(@RequestParam("balance") BigDecimal balance, Model model, @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {
        String email = UserService.getUsernameOfAuthUser(user, oAuth2User);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User authUser = optionalUser.get();
            BigDecimal currentBalance = authUser.getBalance();
            currentBalance = currentBalance.add(balance);
            authUser.setBalance(currentBalance);
            userRepository.save(authUser);
            model.addAttribute("user", authUser);

        }
        return "redirect:/profile";
    }

}
