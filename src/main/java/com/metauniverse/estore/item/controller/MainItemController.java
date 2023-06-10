package com.metauniverse.estore.item.controller;

import com.metauniverse.estore.item.*;
import com.metauniverse.estore.item.enums.ItemAvailability;
import com.metauniverse.estore.item.enums.ItemType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/item")
@AllArgsConstructor
public class MainItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    @GetMapping("/get-item")
    public String getItemById(@RequestParam("itemId") long id, Model model) {
        Optional<Item> item = itemRepository.findById(id);
        model.addAttribute("item", item);
        itemService.defineItemAvailability(id, model);
        return "product";
    }

    @GetMapping("/get-by-type")
    public String getItemByType(@RequestParam("item-type") String type) {
        Iterable<Item> items = itemService.getItemsByType(type);
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
