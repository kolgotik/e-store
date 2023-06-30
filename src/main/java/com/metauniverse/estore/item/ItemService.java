package com.metauniverse.estore.item;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

public interface ItemService {
    Iterable<Item> getItemsByType(String itemType);
    Iterable<Item> getEveryItem();
    Integer getQuantityOfItem(Long id);
    Boolean isItemAvailable(Long id);
    Optional<Item> getItemById(Long id);
    void defineItemAvailability(Long id, Model model);
    Map<Long, String> getItemsImgLinks(Iterable<Item> items, AmazonS3 s3client);
    void setImageLinksIntoSession(Iterable<Item> items);
}
