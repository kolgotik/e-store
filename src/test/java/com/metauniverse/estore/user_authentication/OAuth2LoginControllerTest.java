package com.metauniverse.estore.user_authentication;

import com.metauniverse.estore.controller.login.OAuth2LoginController;
import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OAuth2LoginControllerTest {
    @Mock
    private UserRepository userRepository;

    private OAuth2LoginController oauth2LoginController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        oauth2LoginController = new OAuth2LoginController(userRepository);
    }

    @Test
    void testSaveOAuth2Principal_UserExists() {
        OAuth2User oauth2User = createOAuth2User();
        User existingUser = new User();
        existingUser.setUsername(oauth2User.getAttribute("name"));
        existingUser.setFirstName(oauth2User.getAttribute("given_name"));
        existingUser.setLastName(oauth2User.getAttribute("family_name"));
        existingUser.setEmail(oauth2User.getAttribute("email"));

        when(userRepository.findByUsername(oauth2User.getAttribute("name")))
                .thenReturn(Optional.of(existingUser));

        String result = oauth2LoginController.saveOAuth2Principal(oauth2User);

        verify(userRepository).findByUsername(oauth2User.getAttribute("name"));
        verify(userRepository, never()).save(any());
        assertEquals("redirect:/", result);
    }

    @Test
    void testSaveOAuth2Principal_NewUser() {
        OAuth2User oauth2User = createOAuth2User();
        User newUser = new User();
        newUser.setUsername(oauth2User.getAttribute("name"));
        newUser.setFirstName(oauth2User.getAttribute("given_name"));
        newUser.setLastName(oauth2User.getAttribute("family_name"));
        newUser.setEmail(oauth2User.getAttribute("email"));

        when(userRepository.findByUsername(oauth2User.getAttribute("name")))
                .thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(newUser);

        String result = oauth2LoginController.saveOAuth2Principal(oauth2User);

        verify(userRepository).findByUsername(oauth2User.getAttribute("name"));
        verify(userRepository).save(any());
        assertEquals("redirect:/", result);
    }

    private OAuth2User createOAuth2User() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "john");
        attributes.put("given_name", "John");
        attributes.put("family_name", "Doe");
        attributes.put("email", "john.doe@example.com");

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name())),
                attributes, "name");
    }
}
