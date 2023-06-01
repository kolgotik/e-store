package com.metauniverse.estore.user_creation;


import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.user_service.UserService;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class CreateUserTest {

    private final UserService userService;
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public CreateUserTest(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Test
    public void createUserTest() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Do23e");
        user.setUsername("r2");
        user.setEmail("johndoe1@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("r2"));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);

        Optional<User> userOptional = userRepository.findByUsername("johndoe");
        if (userOptional.isPresent()){
            User savedUser = userOptional.get();
            assertNotNull(savedUser);
            assertEquals(user.getFirstName(), savedUser.getFirstName());
            assertEquals(user.getLastName(), savedUser.getLastName());
            assertEquals(user.getUsername(), savedUser.getUsername());
            assertEquals(user.getEmail(), savedUser.getEmail());
            assertEquals(user.getRoles(), savedUser.getRoles());
        }
    }
}
