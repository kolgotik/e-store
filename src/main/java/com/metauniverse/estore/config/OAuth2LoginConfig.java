/*

package com.metauniverse.estore.config;

import com.metauniverse.estore.service.user_service.CustomOAuth2UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OAuth2LoginConfig {

    private final CustomOAuth2UserService oAuth2UserService;

    public OAuth2LoginConfig(CustomOAuth2UserService oAuth2UserService) {
        this.oAuth2UserService = oAuth2UserService;
    }

    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/login/**", "/")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                //.redirectionEndpoint().baseUri("/login/oauth2/code/google")
                .userInfoEndpoint().userService(oAuth2UserService)
                .and().and().build();
    }

}

*/
