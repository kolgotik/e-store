package com.metauniverse.estore.logout;

import com.metauniverse.estore.cart.Cart;
import com.metauniverse.estore.util.cart_util.SessionCartInitializer;
import com.metauniverse.estore.util.cart_util.impl.SessionCartInitializerImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private SessionCartInitializer cartInitializer = new SessionCartInitializerImpl();
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("SESSION: " + request.getSession().getAttributeNames());
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        log.info("ITEM class list: " + cart.getItems().toString() + " CART-ITEM class list: " + cart.getItemDTOList());
        try {
            request.logout();

        } catch (ServletException e) {
            throw new RuntimeException(e);
        } finally {
            cartInitializer.initSessionCart(session);
        }
    }
}
