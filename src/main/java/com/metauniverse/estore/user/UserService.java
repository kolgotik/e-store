package com.metauniverse.estore.user;

import com.metauniverse.estore.exception.email.EmailAlreadyTakenException;
import com.metauniverse.estore.exception.email.UserNotFoundException;
import com.metauniverse.estore.registration.token.ConfirmationToken;
import com.metauniverse.estore.registration.token.ConfirmationTokenService;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String getUsernameOfAuthUser(User user, OAuth2User oAuth2User) {
        String username = "";
        try {
            if (oAuth2User != null) {
                username = oAuth2User.getAttribute("email");
            } else if (user != null) {
                username = user.getEmail();
            }

        } catch (UserNotFoundException e) {
            log.error(e.getMessage());
        }
        return username;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        boolean isOAuthUser = user.getRoles().contains(Role.ROLE_OAUTH2USER);

        if (userExists && !isOAuthUser) {
            throw new EmailAlreadyTakenException();
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TODO: send email

        return token;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }
}
