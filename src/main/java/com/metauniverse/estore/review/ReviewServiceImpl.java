package com.metauniverse.estore.review;

import com.metauniverse.estore.exception.review.UserDoesNotOwnItemException;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.order.Order;
import com.metauniverse.estore.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private ItemRepository itemRepository;

    @Override
    public boolean doesUserHaveItemToMakeReview(User user, Long itemId) {
        List<Order> userOrders = user.getOrders();
        List<Long> itemsIdsFromEveryOrder = new ArrayList<>();
        List<Item> itemsFromOrder = new ArrayList<>();
        for (Order order : userOrders) {
            itemsFromOrder.addAll(order.getItems());
        }
        for (Item item : itemsFromOrder) {
            itemsIdsFromEveryOrder.add(item.getId());
        }
        if (itemsIdsFromEveryOrder.contains(itemId)) {
            return true;
        } else {
            throw new UserDoesNotOwnItemException();
        }

    }

    @Override
    public List<Review> getItemReviews(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        List<Review> reviews = new ArrayList<>();
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            reviews.addAll(item.getReviews());
        }
        return reviews;
    }
}
