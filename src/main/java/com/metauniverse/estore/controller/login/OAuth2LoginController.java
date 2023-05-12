package com.metauniverse.estore.controller.login;

import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.service.user_service.UserService;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.UUID;

@Controller
@Slf4j
public class OAuth2LoginController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationProvider authenticationProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public OAuth2LoginController(UserService userService, UserRepository userRepository, AuthenticationProvider authenticationProvider) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
    }

    @RequestMapping("/oauth2-success-login")
    public String saveOAuth2Principal(@AuthenticationPrincipal OAuth2User oAuth2User) {

        User userFromDb = userRepository.findByUsername(oAuth2User.getAttribute("name"))
                .orElse(null);

        if (userFromDb == null) {
            userFromDb = new User();
            userFromDb.setRoles(Collections.singleton(Role.ROLE_USER));
            userFromDb.setUsername(oAuth2User.getAttribute("name"));
            userFromDb.setFirstName(oAuth2User.getAttribute("given_name"));
            userFromDb.setLastName(oAuth2User.getAttribute("family_name"));
            userFromDb.setEmail(oAuth2User.getAttribute("email"));
            userFromDb.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            userService.createUser(userFromDb);
        }

        log.info("Authorities of user from db: " + userFromDb.getAuthorities());
        log.info("Attributes of oAuth user: " + oAuth2User.getAttributes());
        log.info("Authorities of oAuth user: " + oAuth2User.getAuthorities());
        log.info("oAuth user id: " + oAuth2User.getName());
        log.info("oAuth user email: " + oAuth2User.getAttribute("email"));

        return "redirect:/";
    }
}
