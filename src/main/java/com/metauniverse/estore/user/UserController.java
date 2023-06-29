package com.metauniverse.estore.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
@AllArgsConstructor
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

}
