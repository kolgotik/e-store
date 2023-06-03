package com.metauniverse.estore.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class GetItemControllerTEST {

    private final ItemRepository itemRepository;

    @GetMapping("/get/{id}")
    public String getItemById(@PathVariable("id") long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.toString();
        } else {
            return "item not found";
        }
    }

    @GetMapping("/get-by-type")
    public String getItemByType(@RequestParam("item-type") String type) {
        Optional<List<Item>> items = itemRepository.getItemsByType(type);
        if (items.isPresent()) {
            return items.toString();
        } else {
            return "no items found";
        }
    }

}
