package com.metauniverse.estore.login.controller;

import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.util.cart_util.SessionCartBinder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@Slf4j
@AllArgsConstructor
public class OAuth2LoginController {

    private final UserRepository userRepository;
    private final SessionCartBinder cartBinder;

    @RequestMapping("/oauth2-success-login")
    public String saveOAuth2Principal(@AuthenticationPrincipal OAuth2User oAuth2User) {

        User userFromDb = userRepository.findByEmail(oAuth2User.getAttribute("email"))
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_OAUTH2USER));
                    newUser.setFirstName(oAuth2User.getAttribute("given_name"));
                    newUser.setLastName(oAuth2User.getAttribute("family_name"));
                    newUser.setEmail(oAuth2User.getAttribute("email"));
                    userRepository.save(newUser);
                    return newUser;
                });
        //cartBinder.bindCartToUser(userFromDb);

        return "redirect:/";
    }
}
