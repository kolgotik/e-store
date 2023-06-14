package com.metauniverse.estore.item;

import org.springframework.ui.Model;

import java.util.Optional;

public interface ItemService {
    Iterable<Item> getItemsByType(String itemType);
    Iterable<Item> getEveryItem();
    Integer getQuantityOfItem(Long id);
    Boolean isItemAvailable(Long id);
    Optional<Item> getItemById(Long id);
    void defineItemAvailability(Long id, Model model);
}
