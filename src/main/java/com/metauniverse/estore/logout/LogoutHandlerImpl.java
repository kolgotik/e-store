package com.metauniverse.estore.logout;

import com.metauniverse.estore.cart.Cart;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            HttpSession session = request.getSession();

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null || !cart.getItems().isEmpty()) {
                Cart newCart = new Cart();
                session.setAttribute("cart", newCart);
            }
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
