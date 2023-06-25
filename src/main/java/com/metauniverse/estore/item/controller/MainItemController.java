package com.metauniverse.estore.item.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.metauniverse.estore.util.s3_util.util.AmazonS3Initializer;
import com.metauniverse.estore.util.s3_util.util.S3BucketDataManager;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.item.ItemRepository;
import com.metauniverse.estore.item.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RequestMapping("/item")
@AllArgsConstructor
public class MainItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final AmazonS3Initializer initializer;
    private final S3BucketDataManager dataManager;
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

    @GetMapping("/item-creation")
    public String showItemCreationPage(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);
        return "item-creation";
    }
    @PostMapping("/create-item")
    public String createItem(@ModelAttribute("item") Item item, @RequestParam("photoFile")MultipartFile photoFile) {
        itemRepository.save(item);
        AmazonS3 client = initializer.init();
        dataManager.putObjectImageLink(item.getId(), item.getPhoto(), client);
        item.setPhoto(null);
        item.setVideo(null);
        itemRepository.save(item);

        return "redirect:/";
    }
}
