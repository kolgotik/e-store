package com.metauniverse.estore.review;

import com.metauniverse.estore.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReviewRepository extends CrudRepository<Review, Long> {

/*
    @Query("SELECT o.id FROM Order o JOIN order_item oi WHERE o.user = ?1 AND oi.item.id = ?2")
*/
  //  boolean doesUserHaveItemToMakeReview(User user, Long itemId);

}
