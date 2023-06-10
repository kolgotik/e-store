package com.metauniverse.estore.cart;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CartRepository extends CrudRepository<Cart, Long> {

}
