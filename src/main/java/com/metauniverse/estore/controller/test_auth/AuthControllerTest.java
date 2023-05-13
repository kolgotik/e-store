package com.metauniverse.estore.controller.test_auth;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerTest {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/auth")
    public String testAuth(){
        return "Authenticated " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
