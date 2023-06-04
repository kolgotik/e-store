package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.item.ItemType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
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
        Iterable<Item> items = itemRepository.getItemsByType(type);
        if (items != null) {
            return items.toString();
        } else {
            return "no items found";
        }
    }

    @GetMapping("/create-item")
    public String createItem() {

        Item item = new Item();
        item.setItemType(ItemType.CONSOLE.getValue());
        item.setCategory("sony console");
        item.setBrand("SONY");
        item.setName("PS4 1TB");
        item.setPrice(BigDecimal.valueOf(356.43));

        itemRepository.save(item);

        return "redirect:/";
    }

}
