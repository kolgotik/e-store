package com.metauniverse.estore.cart;

import com.metauniverse.estore.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CartRepository extends CrudRepository<Cart, Long> {
   /* @Query("SELECT c FROM Cart c WHERE c.userId = ?1")
    Optional<Cart> findByUserId(User user);*/
}
