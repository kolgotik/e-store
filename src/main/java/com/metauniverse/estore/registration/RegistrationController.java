package com.metauniverse.estore.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/register-form")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
