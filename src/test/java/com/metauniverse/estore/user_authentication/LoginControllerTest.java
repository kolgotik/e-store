package com.metauniverse.estore.user_authentication;

import com.metauniverse.estore.login.controller.LoginController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @Mock
    private AuthenticationProvider authenticationProvider;

    @InjectMocks
    private LoginController loginController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessLogin_Success() {
        String username = "john";
        String password = "secret";

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationProvider.authenticate(any())).thenReturn(authentication);

        String result = loginController.processLogin(username, password);

        verify(authenticationProvider).authenticate(any());
        assertEquals("redirect:/", result);
    }

    @Test
    void testProcessLogin_AuthenticationException() {
        String username = "john";
        String password = "wrong-password";

        when(authenticationProvider.authenticate(any())).thenThrow(new AuthenticationException("Invalid credentials") {
        });

        String result = loginController.processLogin(username, password);

        verify(authenticationProvider).authenticate(any());
        assertEquals("redirect:/login?error", result);
    }
}
