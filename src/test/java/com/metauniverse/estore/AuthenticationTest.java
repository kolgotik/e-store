package com.metauniverse.estore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setRoles(Collections.singleton(Role.ROLE_USER));
        user.setPassword(bCryptPasswordEncoder.encode("testpassword"));
        userRepository.save(user);
    }

    @AfterEach
    public void teardown() {
        userRepository.delete(user);
    }

    @Test
    public void shouldDenyAccessWithInvalidCredentials() throws Exception {
        Map<String, String> loginCredentials = new HashMap<>();
        loginCredentials.put("username", "testuser");
        loginCredentials.put("password", "wrongpassword");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginCredentials)))
                .andExpect(status().isUnauthorized())
                .andExpect(cookie().doesNotExist("JSESSIONID"));
    }

}

