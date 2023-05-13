package com.metauniverse.estore.controller.login;

import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@Slf4j
public class OAuth2LoginController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public OAuth2LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/oauth2-success-login")
    public String saveOAuth2Principal(@AuthenticationPrincipal OAuth2User oAuth2User) {

        User userFromDb = userRepository.findByUsername(oAuth2User.getAttribute("name"))
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setRoles(Collections.singleton(Role.ROLE_USER));
                    newUser.setUsername(oAuth2User.getAttribute("name"));
                    newUser.setFirstName(oAuth2User.getAttribute("given_name"));
                    newUser.setLastName(oAuth2User.getAttribute("family_name"));
                    newUser.setEmail(oAuth2User.getAttribute("email"));
                    userRepository.save(newUser);
                    return newUser;
                });

        log.info("Authorities of user from db: " + userFromDb.getAuthorities());
        log.info("Attributes of oAuth user: " + oAuth2User.getAttributes());
        log.info("Authorities of oAuth user: " + oAuth2User.getAuthorities());
        log.info("oAuth user id: " + oAuth2User.getAttribute("sub"));
        log.info("oAuth user email: " + oAuth2User.getAttribute("email"));
        log.info("Auth info: " + SecurityContextHolder.getContext().getAuthentication().getName());

        return "redirect:/";
    }
}
