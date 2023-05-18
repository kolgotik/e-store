package com.metauniverse.estore.registration;

import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.user_service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class RegistrationService {

    private static final String EMAIL_NOT_VALID_MSG = "Email: %s is not valid";

    private final EmailValidator emailValidator;

    private final UserService userService;

    public String registerUser(RegistrationRequest request) {

        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid) {
            throw new IllegalStateException(String.format(EMAIL_NOT_VALID_MSG, request.getEmail()));
        }

        return userService.signUpUser(
                new User(request.getFirstName(),
                        request.getLastName(),
                        request.getFirstName() + " " + request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        Collections.singleton(Role.ROLE_USER))
        );
    }
}
