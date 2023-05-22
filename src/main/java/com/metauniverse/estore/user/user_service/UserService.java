package com.metauniverse.estore.user.user_service;

import com.metauniverse.estore.exception.EmailAlreadyTakenException;
import com.metauniverse.estore.exception.UserWithEmailNotFoundException;
import com.metauniverse.estore.registration.token.ConfirmationToken;
import com.metauniverse.estore.registration.token.ConfirmationTokenService;
import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserWithEmailNotFoundException(email));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        boolean isOAuthUser = user.getRoles().contains(Role.ROLE_OAUTH2USER);

        if (userExists && !isOAuthUser) {
            throw new EmailAlreadyTakenException(user.getEmail());
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
