package com.metauniverse.estore.user_creation;


import com.metauniverse.estore.dto.user_dto.UserDTO;
import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.service.user_service.UserService;
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
        user.setLastName("Doe");
        user.setUsername("johndoe1");
        user.setEmail("johndoe1@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("password"));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userService.createUser(user);

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
