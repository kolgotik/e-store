package com.metauniverse.estore.item;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
@AllArgsConstructor
public class CreateItemControllerTEST {

    private final ItemRepository itemRepository;

    @GetMapping("/create-item")
    public String createItem() {
        Item item = new Item();
        item.setItemType("smartphone");
        item.setName("OnePlus Nord CE 3 Lite 5G");
        item.setPrice(135_000);

        itemRepository.save(item);

        return item.toString();
    }

}
