package com.metauniverse.estore.login.controller;

import com.metauniverse.estore.registration.RegistrationRequest;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@Slf4j
@AllArgsConstructor
public class LoginController {

    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage(RegistrationRequest request, Model model) {
        model.addAttribute("request", request);
        return "login";
    }

    @PostMapping("/process-login")
    public String processLogin(@RequestParam String email, @RequestParam String password) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            Authentication authentication = authenticationProvider.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } catch (AuthenticationException e) {
            return "redirect:/login?error";
        }
    }
}
