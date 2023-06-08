package com.metauniverse.estore.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public Iterable<Item> getItemsByType(String itemType) {
        return itemRepository.getItemsByType(itemType);
    }
    public Iterable<Item> getEveryItem() {
        return itemRepository.findAll();
    }
    public Integer getQuantityOfItem(Long id) {
        return itemRepository.getQuantityOfItem(id);
    }
    public Boolean isItemAvailable(Long id) {
        Integer quantity = itemRepository.getQuantityOfItem(id);
        if (quantity == null || quantity == 0) {
            return false;
        }
        return true;
    }
}
