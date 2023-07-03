package com.metauniverse.estore.review;

import com.metauniverse.estore.user.User;

import java.util.List;

public interface ReviewService {

    boolean doesUserHaveItemToMakeReview(User user, Long itemId);

    List<Review> getItemReviews(Long itemId);

}
