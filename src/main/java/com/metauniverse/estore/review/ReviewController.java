package com.metauniverse.estore.review;

import com.metauniverse.estore.exception.review.UserDoesNotOwnItemException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.user.User;
import com.metauniverse.estore.user.UserRepository;
import com.metauniverse.estore.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/review")
@AllArgsConstructor
@Slf4j
public class ReviewController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/send-review")
    public String sendReview(@RequestParam("itemId") Long itemId,
                             @RequestParam("score") Float score,
                             @RequestParam("reviewText") String reviewText,
                             @AuthenticationPrincipal OAuth2User oAuth2User, @AuthenticationPrincipal User user) {

        String email = UserService.getUsernameOfAuthUser(user, oAuth2User);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        try {
            boolean userHasItem = reviewService.doesUserHaveItemToMakeReview(optionalUser.get(), itemId);
            if (userHasItem) {
                Review review = new Review();
                review.setUser(optionalUser.get());
                review.setTextReview(reviewText);
                review.setItem(optionalItem.get());
                review.setScore(score);
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                String formattedDateTime = dateTime.format(formatter);
                review.setCreatedAt(formattedDateTime);
                review.setUserHasItem(true);
                reviewRepository.save(review);
            }
        } catch (UserDoesNotOwnItemException e) {
            log.error(e.getMessage());
            return "review-add-err";
        }
        return "redirect:/item/get-item?itemId=" + itemId;
    }

}
