package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemControllerTEST {

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

    @GetMapping("/create-item")
    public String createItem() {

        Item item = new Item();
        item.setItemType("android smartphone");
        item.setCategory("mid-range smartphone");
        item.setBrand("OnePlus");
        item.setName("OnePlus Nord CE 3 Lite 5G");
        item.setPrice(BigDecimal.valueOf(300.81));

        itemRepository.save(item);

        return item.toString();
    }

}
