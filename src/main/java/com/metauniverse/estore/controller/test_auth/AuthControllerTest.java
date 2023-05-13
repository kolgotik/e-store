package com.metauniverse.estore.controller.test_auth;

import com.metauniverse.estore.user.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerTest {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth")
    public String testAuth(@AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {

        String username;

        if (oAuth2User != null) {
            username = oAuth2User.getAttribute("name");
        } else if (user != null) {
            username = user.getUsername();
        } else {
            username = "Oops! Something is wrong";
        }
        return "Authenticated " + username;
    }

}
