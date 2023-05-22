package com.metauniverse.estore.registration;

import com.metauniverse.estore.exception.InvalidEmailException;
import com.metauniverse.estore.registration.token.ConfirmationToken;
import com.metauniverse.estore.registration.token.ConfirmationTokenService;
import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.user_service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;

    private final EmailValidator emailValidator;

    private final UserService userService;

    private final ConfirmationTokenService confirmationTokenService;

    public String registerUser(RegistrationRequest request) {

        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid) {
            throw new InvalidEmailException(request.getEmail());
        }


        //TODO: THIS COULD BE THE ISSUE
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        log.info("USER ROLE: " + user.getRoles());
        if (user.getRoles().contains(Role.ROLE_OAUTH2USER)) {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getFirstName() + " " + request.getLastName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRoles(Collections.singleton(Role.ROLE_OAUTH2USER));

            return userService.signUpUser(user);
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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);

        userService.enableUser(confirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
