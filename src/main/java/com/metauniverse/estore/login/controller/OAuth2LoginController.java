package com.metauniverse.estore.login.controller;

import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Set;

@Controller
@Slf4j
@AllArgsConstructor
public class OAuth2LoginController {

    private final UserRepository userRepository;

    @RequestMapping("/oauth2-success-login")
    public String saveOAuth2Principal(@AuthenticationPrincipal OAuth2User oAuth2User) {

        User userFromDb = userRepository.findByEmail(oAuth2User.getAttribute("email"))
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_OAUTH2USER));
                    newUser.setFirstName(oAuth2User.getAttribute("given_name"));
                    newUser.setLastName(oAuth2User.getAttribute("family_name"));
                    newUser.setEmail(oAuth2User.getAttribute("email"));
                    newUser.setBalance(BigDecimal.valueOf(0));
                    userRepository.save(newUser);
                    return newUser;
                });
        if (userFromDb.getBalance() == null) {
            userFromDb.setBalance(BigDecimal.valueOf(0));
            userRepository.save(userFromDb);
        }
        return "redirect:/";
    }
}
