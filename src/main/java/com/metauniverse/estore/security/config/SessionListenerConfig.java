package com.metauniverse.estore.security.config;

import com.metauniverse.estore.listener.SessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionListenerConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionListener> sessionListenerServletListenerRegistrationBean() {
        ServletListenerRegistrationBean<SessionListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new SessionListener());
        return registrationBean;
    }

}
