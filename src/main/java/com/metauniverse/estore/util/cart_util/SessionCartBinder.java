package com.metauniverse.estore.util.cart_util;

import com.metauniverse.estore.user.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SessionCartBinder {

    void bindCartToUser(User user);
    void bindCartToUser(String email);
    void bindCartToUser(OAuth2User oAuth2User);

}
