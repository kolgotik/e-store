package com.metauniverse.estore.service.user_service;

import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.Role;
import com.metauniverse.estore.user.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User googleOAuth2User = super.loadUser(userRequest);

        Map<String, Object> userAttributes = googleOAuth2User.getAttributes();

        User user = new User();
        user.setUsername((String) userAttributes.get("sub"));
        user.setEmail((String) userAttributes.get("email"));
        user.setFirstName((String) userAttributes.get("given_name"));
        user.setLastName((String) userAttributes.get("family_name"));
        user.setRoles(Collections.singleton(Role.ROLE_USER));

        userRepository.save(user);

       /* Optional<User> optionalUser = userRepository.findById(Integer.valueOf(userId));
        User dbUser = optionalUser.orElseGet( () -> {
            User newUser = new User();
            newUser.setId(Integer.valueOf(userId));
            newUser.setEmail(email);
            newUser.setUsername(name);
            newUser.setRoles(Collections.singleton(Role.ROLE_USER));
            return userRepository.save(newUser);
        });*/

        return googleOAuth2User;
    }
}
