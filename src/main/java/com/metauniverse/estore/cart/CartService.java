package com.metauniverse.estore.cart;

import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

  /*  public Optional<Cart> getCartByUserId(User user) {
        return cartRepository.findByUserId(user);
    }*/

}
