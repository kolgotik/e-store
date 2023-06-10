package com.metauniverse.estore.item;

import com.metauniverse.estore.item.enums.ItemAvailability;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Optional;

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
    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }
    public void defineItemAvailability(Long id, Model model) {
        Boolean isAvailable = isItemAvailable(id);
        Integer itemQuantity = getQuantityOfItem(id);
        if (isAvailable) {
            model.addAttribute("availability", ItemAvailability.ITEM_AVAILABLE.getValue());
            if (itemQuantity <= 5) {
                model.addAttribute("availability", String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
                model.addAttribute("fewLeftMSG", String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
                System.out.println(String.format(ItemAvailability.FEW_ITEMS_LEFT.getValue(), itemQuantity));
            }
        } else {
            model.addAttribute("availability", ItemAvailability.ITEM_UNAVAILABLE.getValue());
        }
    }
}
